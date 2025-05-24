let idRoom = room.id;


const nameRoomEl = document.getElementById('name-room');
const descriptionRoomEl = document.getElementById('description');
const roomTypeEl = document.getElementById('room-type');
const areaRoomEl = document.getElementById('area-room');
const quantityRoomEl = document.getElementById('quantity');
const capacityRoomEl = document.getElementById('capacity');
const statusRoomEl = document.getElementById('status');
const bedSizeRoomEl = document.getElementById('bed-size');
const bedTypeRoomEl = document.getElementById('bed-type');
const saveRoom = document.getElementById('save');
const imageRoom = document.getElementById('imageRoom');
const btnDeleteImage = document.getElementById('btn-delete-image');
const priceDefaultEl = document.getElementById('priceDefault');

imageRoom.addEventListener('change', (e)=>{
    const file = e.target.files[0];

    // tạo một form data với key là file và giá trị trong input
    const formData = new FormData();
    formData.append('file', file);

    axios.post('/api/hotel-manager/room/upload-image/'+idRoom ,formData)
        .then((res) =>{
            toastr.success("Upload thành công ");
            getAllImageRoom(idRoom,currentPage)
        })
        .catch((err) =>{
            console.log(err);
            toastr.error("Upload thất bại ");
        })
})

saveRoom.addEventListener('click', (e) => {
    e.preventDefault();
    if (!$('#form-name-room').valid()) return;
    if (!$('#form-description-room').valid()) return;

    let listId = $('#bedroom').val();
    listId = listId.concat($('#bathroom').val());
    listId = listId.concat($('#entertainment').val());
    listId = listId.concat($('#kitchen').val());
    listId = listId.concat($('#internet').val());
    listId = listId.concat($('#others').val());
    listId = listId.map(e => parseInt(e));

    let status = false;
    if (statusRoomEl.value === '1') {
        status = true;
    }


    console.log(statusRoomEl.value == "1")
    const data = {
        nameRoom : nameRoomEl.value,
        descriptionRoom : descriptionRoomEl.value,
        amenityRoom : listId,
        roomType :roomTypeEl.value,
        area :  areaRoomEl.value,
        capacity : capacityRoomEl.value,
        quantity :quantityRoomEl.value,
        bedType : bedTypeRoomEl.value,
        bedSize : bedSizeRoomEl.value,
        priceDefault : priceDefaultEl.value,
        statusRoom :status
    };

    console.log(data)

    axios.put('/api/hotel-manager/room/update/'+idRoom , data)
        .then((res) =>{
            toastr.success("Cập nhật phòng thành công")
            console.log(res.data)
            renderDataRoom(res.data)
        })
        .catch((err) =>{
            console.log(err);
            toastr.error(err.response.data.message);

        })


})
const renderDataRoom = (room) => {
    nameRoomEl.value = room.name
    let amenityIds = room.amenityRoomList.map(ame => ame.id);
    const fields = ['#bathroom', '#bedroom', '#entertainment', '#kitchen', '#internet', '#others'];
    fields.forEach(field => {
        $(field).val(amenityIds);
        $(field).trigger('change');
    });
    descriptionRoomEl.value = room.description;
    roomTypeEl.value = room.roomType;
    bedSizeRoomEl.value = room.bedSize;
    bedTypeRoomEl.value = room.bedType;
    areaRoomEl.value = room.area;
    quantityRoomEl.value = room.quantity;
    capacityRoomEl.value = room.capacity;
    priceDefaultEl.value = room.priceDefault;
    statusRoomEl.value =room.status ? '1' : '0';

}



let  currentPage = 1;
let totalPages = null;

// gọi api lấy danh sách ảnh
const getAllImageRoom = (roomId,currentPage) =>{
    axios.get(`/api/hotel-manager/room/get-image/${roomId}?page=${currentPage}`)
        .then((res) =>{
            totalPages = res.data.totalPages
            renderPagination(totalPages)
            renderImage(res.data.content);
        }).catch((err) =>{{
        console.log(err);
    }})
}


const imageContainer = document.querySelector('.image-container');
// hiển thị ảnh cho room hiện tại
const renderImage = (listImage) =>{
    if (listImage.length === 0){
        console.log(listImage.length === 0)
        imageContainer.innerHTML = `<div > Chưa có ảnh được thêm cho phòng này </div>`;
        return
    }
    let htmlImage = "";
    listImage.forEach(image => {
        htmlImage += `<div class="image-item" onclick="choseImage(this)" data-id="${image.id}">
                                <img src="${image.url}"
                                     alt="">
                       </div>`;
    });
    imageContainer.innerHTML = htmlImage;
}




// phân trang cho danh sách ảnh
const renderPagination = totalPages => {
    let html = "";
    for (let i = 1; i <= totalPages; i++) {
        html += `
            <li class="page-item ${i === currentPage ? 'active' : ''}">
              <a class="page-link" onclick="chosePage(${i})">${i}</a>
            </li>
        `
    }
    document.querySelector('.pagination-container').innerHTML = `
        ${totalPages > 1 ? `
            <nav aria-label="...">
              <ul class="pagination">
                <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                  <a class="page-link" onclick="prevPage()"><i class="fa-solid fa-angle-left"></i></a>
                </li>
                ${html}
                <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                  <a class="page-link" onclick="nextPage()"><i class="fa-solid fa-angle-right"></i></a>
                </li>
              </ul>
            </nav>
        ` : ""}
    `;
}

// lấy data trang được chọn
const chosePage = index => {
    currentPage = index ;
    getAllImageRoom(idRoom,currentPage)
}

// lây data page trước trang hiện tại
const prevPage = () => {
    if (currentPage > 1) {
        currentPage--;
        getAllImageRoom(idRoom,currentPage)
    }
}

// lây data page tiếp theo
const nextPage = () => {
    if (currentPage < totalPages) {
        currentPage++;
        getAllImageRoom(idRoom,currentPage)
    }
}

// highlight ảnh được chọn
const choseImage = el => {
    const selectedEl = document.querySelector(".image-item.selected");
    if (selectedEl) {
        selectedEl.classList.remove("selected");
    }

    el.classList.add("selected");
    btnDeleteImage.disabled = false;
}
btnDeleteImage.addEventListener("click", async () => {
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl) {
        toastr.warning("Vui lòng chọn ảnh cần xóa");
        return;
    }

    const imageId = selectedEl.dataset.id;

    try {
        await axios.delete(`/api/hotel-manager/room/delete-image/${imageId}`)
        btnDeleteImage.disabled = true;
        toastr.success("Xoá ảnh thành công");

        // Hiển thị ảnh đã upload
        currentPage = 1
        getAllImageRoom(idRoom,currentPage);
    } catch (err) {
        console.log(err);
        toastr.error(err.response.data.message);
    }
})
const  btnDelete = document.getElementById('delete');
btnDelete.addEventListener("click" ,()=>{
    const isConfirm = confirm("Bạn có chắc mình muốn xóa phòng này không?");
    if (!isConfirm) return;
    axios.delete("/api/hotel-manager/room/delete/"+idRoom)
        .then((res) =>{
            toastr.success("Xóa thành công ")
            setTimeout(()=>{
                window.location.href = '/hotel-manager/room'
            },2000)
        }).catch((err) =>{
        console.log(err)
        toastr.error(err.response.data.message)
    })

})
$('#form-name-room').validate({
    rules: {
        nameRoom: {
            required: true,
        },
    }, messages: {
        nameRoom: {
            required: "Vui lòng nhập tên phòng",
        },
    }, errorElement: 'span', errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }, highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    }, unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});
$('#form-description-room').validate({
    rules: {
        description: {
            required: true,
        }, area: {
            required: true,
            number: true
        }, capacity: {
            required: true,
            number: true
        }, quantity: {
            required: true,
            number: true
        },
    }, messages: {
        description: {
            required: "Vui lòng nhập mô tả về phòng",
        }, area: {
            required: "Vui lòng nhập diện tích phòng",
            number: "Vui lòng nhập một số",
        }, capacity: {
            required: "Vui lòng nhập sức chứa phòng",
            number: "Vui lòng nhập một số",
        }, quantity: {
            required: "Vui lòng nhập số lượng phòng ",
            number: "Vui lòng nhập một số",
        },
    }, errorElement: 'span', errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }, highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    }, unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

getAllImageRoom(idRoom,currentPage)
renderDataRoom(room);