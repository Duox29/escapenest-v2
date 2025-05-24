// không sử dụng filter(lấy tât cả booking)
const getAllBooking = () => {
    renderBooking(bookingList);
    document.querySelector('[type-filter="ALL"]').classList.add('current-filter');
}

// lọc tất cả các booking theo trạng thái
const getBookingConfirm = () => {
    const lists = bookingList.filter(booking => booking.statusBooking === "CONFIRMED");
    renderBooking(lists);
    // Xóa lớp current-filter từ tất cả các nút
    document.querySelectorAll('.btn-filter-booking').forEach(btn => {
        btn.classList.remove('current-filter');
    });

    // Thêm lớp current-filter vào button type-filter="CONFIRMED"
    document.querySelector('[type-filter="CONFIRMED"]').classList.add('current-filter');
}
const getBookingComplete = () => {
    const lists = bookingList.filter(booking => booking.statusBooking === "COMPLETE")
    renderBooking(lists);

    // Xóa lớp current-filter từ tất cả các nút
    document.querySelectorAll('.btn-filter-booking').forEach(btn => {
        btn.classList.remove('current-filter');
    });

    // Thêm lớp current-filter vào button type-filter="COMPLETE"
    document.querySelector('[type-filter="COMPLETE"]').classList.add('current-filter');
}
const getAllBookingCancel = () => {
    const lists = bookingList.filter(booking => booking.statusBooking === "CANCELLED")
    renderBooking(lists);
    // Xóa lớp current-filter từ tất cả các nút
    document.querySelectorAll('.btn-filter-booking').forEach(btn => {
        btn.classList.remove('current-filter');
    });

    // Thêm lớp current-filter vào button type-filter="CANCELLED"
    document.querySelector('[type-filter="CANCELLED"]').classList.add('current-filter');
}


// function render ra dữ liệu khi click vào btn filter
const renderBooking = (lists) => {
    let html = '';
    const containerBookingList = document.querySelector(".list-booking ul");


    lists.forEach(booking => {
        const checkIn = booking.checkIn.split('-');
        const checkOut = booking.checkOut.split('-');

        let statusBooking = getStatusBooking(booking.statusBooking);

        html += `
             <li class="w-100" >
                                    <a class="card-booking" href="/chi-tiet-booking/${booking.id}">
                                        <img src="${booking.hotel.city.imageCity}" alt="Image">
                                        <div class="infor-booking">
                                            <h5>${booking.hotel.name}</h5>
                                            <h6 >${booking.hotel.city.name}</h6>
                                            <span>${checkIn[2]}
                                                tháng
                                                ${checkIn[1]}
                                                - ${checkOut[2]}
                                                tháng
                                                ${checkOut[1]}
                                            </span>
                                            <p class="p-0 m-0" >${statusBooking}</p>
                                        </div>
                                    </a>
                                </li>
        `;
    })
    containerBookingList.innerHTML = html;

}
const btnSearch = document.querySelector('.search-id-booking');


// lấy value của trạng thái
const getStatusBooking = (statusBooking) => {
    switch (statusBooking) {
        case "PENDING":
            return "Chờ xác nhận";
        case "CONFIRMED":
            return "Đã xác nhận";
        case "CANCELLED":
            return "Đã hủy";
        case "COMPLETE":
            return "Hoàn tất";

    }
}


// function tìm kiếm theo id booking
const searchBooking = () => {
    if (btnSearch.value === "") {
        renderBooking(bookingList);
        return;
    }
    console.log(btnSearch.value)
    axios.get("/api/booking/" + btnSearch.value)
        .then((res) => {
            console.log(res)
            console.log("Thành công")
            renderBooking(res.data)
        })
        .catch((err) => {

            console.log("Thất công")
        })
}


// sắp xếp list booking theo các option
const optionsSort = document.getElementById('select-sort');

optionsSort.addEventListener('change',()=>{
    console.log(optionsSort.value)
    const listBooking = bookingList.slice();

    switch (optionsSort.value) {

        case "DEFAULT":
            renderBooking(bookingList);
            break;
        case "DATE-CHECKIN":
            const sortBookingByCheckIn = listBooking.sort((a,b) =>{
                return new Date(a.checkIn) - new Date(b.checkIn);
            } )
            renderBooking(sortBookingByCheckIn);
            break;
        case "DATE-CHECKOUT":
            const sortBookingByCheckOut = listBooking.sort((a,b) =>{
                return new Date(a.checkOut) - new Date(b.checkOut);
            } )
            renderBooking(sortBookingByCheckOut);
            break;


    }
})

renderBooking(bookingList);