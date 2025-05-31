
function formatDate(date) {
    let year = date.getFullYear(); // Sử dụng getFullYear() để lấy năm đầy đủ
    let month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng được đánh số từ 0-11, cần +1 và đảm bảo có 2 chữ số
    let day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
    return `${year}-${month}-${day}`;
}

let startDate = formatDate(new Date());
let endDate =  formatDate(moment().add(4, 'days').toDate());

$(function() {
    // Khởi tạo daterangepicker và thiết lập ngày mặc định
    $('#date-range').daterangepicker({
        opens: 'left',
        startDate: new Date(),
        endDate:  moment().add(4, 'days').toDate(),
        minDate: new Date()
    }, function(start, end, label) {
        // Khi người dùng chọn ngày, cập nhật giá trị của các biến bên ngoài
        startDate = start.format('YYYY-MM-DD');
        endDate = end.format('YYYY-MM-DD');
    });
});
// slider
$('.owl-carousel').owlCarousel({
    loop:true,
    margin:15,
    autoHeight: false,
    nav:true,
    navText:['<i class="fa-solid fa-angle-left"></i>','<i class="fa-solid fa-angle-right"></i>'],
    dots: false,
    responsive:{
        0:{
            items:2.2
        },
        600:{
            items:3.2
        },
        1000:{
            items:4.2
        }
    }
})


// tùy chọn quantity cho room và  guest
const minusGuest = document.querySelector(".minus-guest");// nút tăng số lượng khách hàng
let numberGuest = document.querySelector(".num-guest"); // quantity guest
const plusGuest = document.querySelector(".plus-guest");//nút giảm số lượng khách hàng
let countGuest = 1;

minusGuest.addEventListener('click',()=>{
    if(countGuest<=1){
        return
    }

    countGuest--;
    countGuest=(countGuest<10)?'0'+countGuest : countGuest;
    numberGuest.innerText = countGuest;
})

plusGuest.addEventListener('click',()=>{
    if(countGuest>=20){
        return
    }
    countGuest++;
    countGuest=(countGuest<10)?'0'+countGuest : countGuest;
    numberGuest.innerText = countGuest;
})
const minusRoom = document.querySelector(".minus-room");
let numberRoom = document.querySelector(".num-room")
const plusRoom = document.querySelector(".plus-room")

let countRoom = 1;
minusRoom.addEventListener('click',()=>{
    if(countRoom<=1){
        return
    }
    countRoom--;
    countRoom=(countRoom<10)?'0'+countRoom : countRoom;
    numberRoom.innerText = countRoom;
})

plusRoom.addEventListener('click',()=>{
    if(countRoom >=20){
        return
    }
    countRoom++;
    countRoom=(countRoom<10)?'0'+countRoom : countRoom;
    numberRoom.innerText = countRoom
})




// xử lý input data cho tìm kiếm
const btnSearch = document.getElementById('btn-search');
const inputNameCity = document.getElementById('input-name-city'); // input dữ liệu thành phố người dùng nhập vào

const showHotelCity = document.getElementById("show-hotel-city");// btn chuyển hướng từ gợi ý homestay theo thanh phố

// chuyển hướng tới trang sanh sách khi người dùng click vào ô tìm kiếm
btnSearch.addEventListener('click' ,()=>{
    if (!$('#name-city').valid()) return;
    let nameCity = inputNameCity.value;
    navigation(nameCity);

});

inputNameCity.addEventListener('keydown',(e)=>{
    if (!$('#name-city').valid()) return;

    if (e.key === 'Enter'){
        navigation(inputNameCity.value);
    }
})

// click vào các homestay sẽ chuyển hướng tới trang danh sách homestay theo thành phố người dùng đã chọn
showHotelCity.addEventListener('click' , (e)=>{
    let nameCity= showHotelCity.getAttribute('value');
    console.log(nameCity);
    navigation(nameCity);
})


const sliderCity = document.querySelectorAll('.outstanding-city');
sliderCity.forEach(city =>{

})
const  choiceOutstandingCity = () =>{
    let nameCity= showHotelCity.getAttribute('value');
    console.log(nameCity);
    navigation(nameCity);
}


// chuyển hướng tới trang danh sách
const navigation = (inputNameCity) =>{
    window.location.href="/danh-sach-khach-san?" +
        "nameCity=" + inputNameCity + "&checkIn=" + startDate +
        "&checkOut=" + endDate + "&numberGuest=" +
        numberGuest.textContent + "&numberRoom=" + numberRoom.textContent;
}

const btnList = document.querySelectorAll('.btn-name-city');
const listCardHotel = document.getElementById('list-card')

// dữ liệu mặc định
let defaultValue = btnList[0];
// khi vào trang thì gọi render dữ liệu mặc định cho recommend hotel
window.addEventListener('load', () => {
    defaultValue.classList.add('active-city')
    console.log(startDate);
    console.log(endDate);
    // Gọi API để lấy dữ liệu cho thành phố mặc định và render homestay
    renderCityData(defaultValue.textContent ,startDate,endDate);
    showHotelCity.innerHTML = `<span>Xem tất cả homestay tại ${defaultValue.textContent}</span>`
    showHotelCity.setAttribute('value', defaultValue.textContent);
});




// lấy value của các tham sô tìm kiếm
btnList.forEach((btn)=>{
    btn.addEventListener('click' , (qualifiedName, value)=>{
        btnList.forEach((btns) =>{
            btns.classList.remove('active-city');
        })

        btn.classList.add("active-city")
        let city = btn.textContent;
        // showHotelCity.innerText = 'Xem thêm homestay của ' + city;
        showHotelCity.innerHTML = `<span>Xem tất cả homestay tại ${city}</span>`
        renderCityData(city,startDate,endDate);
    })
})



// gọi api để render dữ liệu
const renderCityData = (value,checkIn,checkOut) => {
    axios.get('/api/search/all/' + value+"?checkIn=" + checkIn +
    "&checkOut=" + checkOut)
        .then((res) => {
            console.log(res)
            console.log(res.data)
            let data = res.data;
            renderHotel(data,value);
        })
        .catch((err) => {
           console.log(err)
        });
};

const formatCurrency = (number)=> {
    return number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}
// render homestay recommend


function navigateToHotelDetail(hotelId, encodedCity) {
    //const city = decodeURIComponent(encodedCity);
    // Lấy giá trị startDate và endDate tại thời điểm click
    const currentStartDate = startDate;
    const currentEndDate = endDate;
    window.location.href = `/chi-tiet-khach-san/${hotelId}?nameCity=${encodedCity}&checkIn=${currentStartDate}&checkOut=${currentEndDate}`;
}

const renderHotel = (data,city) =>{

    let html = '';
    let count = 1 ;
    data.forEach((hotel)=> {
        if (count > 8) {
            return;
        }
        html += `
                    <div class="col-3 h-100">
                        <a class="card mb-5 text-reset text-decoration-none  h-100" onclick="navigateToHotelDetail(${hotel.id},'${city.replace(/'/g, "\\'")}')">
                            <img class="image-hotel" src="${hotel.poster}" alt="Ảnh Hotel">
                            <div class="p-2 h-100">
                                <div class="d-flex justify-content-start align-content-center h-100">
                                    <span class="p-1 m-0 score-rating h-100">${hotel.rating.toFixed(1)}</span>
                                    <span class="p-1 m-0 h-100" >${hotel.ratingText}</span>
                                    <span  class="p-1 m-0 h-100" >(${hotel.totalReviews} nhận xét)</span>
                                </div>

                                <h5 class="p-0 m-0 h-100" >${hotel.name}</h5>
                                <p class="p-0 m-0 d address-hotel h-100" > <i class="fa-solid fa-location-dot"></i> ${hotel.address}</p>
                                <div class="price list-unstyledp-0 m-0">


<!--                                            <div class="p-0 wrapper w-100 d-flex">-->
<!--                                                <span class="original-price "><del>1.300.500 ₫</del></span>-->
<!--                                            </div>-->
                                            <h4 class="p-0 current-price w-100 d-flex">${formatCurrency(hotel.estimatedPrice)}</h4>
                                            <p class="description-price w-100 pb-1 m-0">Giá mỗi đêm đã bao gồm thuế & phí</p>
<!--                                            <span class="discount mt-3"> Giảm 15%</span>-->


                                   </div>
                            </div>
                        </a>
                    </div>
                    `
        count++;

    })
    listCardHotel.innerHTML = html;


}






