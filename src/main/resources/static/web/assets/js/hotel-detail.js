function formatDate(date) {
    let year = date.getFullYear(); // Sử dụng getFullYear() để lấy năm đầy đủ
    let month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng được đánh số từ 0-11, cần +1 và đảm bảo có 2 chữ số
    let day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
    return `${year}-${month}-${day}`;
}
// đăặt ngày bắt đầu và ngày kêt thúc mặc định
let startDate = formatDate(new Date());
let endDate =  formatDate(moment().add(4, 'days').toDate());
//  thư viện chọn ngày
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

const stars = document.querySelectorAll(".star");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        const rating = parseInt(star.getAttribute("data-rating"));
        highlightStars(rating);
    });

    star.addEventListener("mouseout", () => {
        resetStars();
        highlightStars(currentRating);
    });

    star.addEventListener("click", () => {
        currentRating = parseInt(star.getAttribute("data-rating"));
        ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
        highlightStars(currentRating);
    });
});

function resetStars() {
    stars.forEach((star) => {
        star.classList.remove("active");
    });
}

function highlightStars(rating) {
    stars.forEach((star) => {
        const starRating = parseInt(star.getAttribute("data-rating"));
        if (starRating <= rating) {
            star.classList.add("active");
        }
    });
}

// render thông tin tìm kiếm trước đó của người dùng
// chọn sô lượng người và phòng

let minusGuest = document.querySelector(".minus-guest");
let numberGuest = document.querySelector(".num-guest")
let plusGuest = document.querySelector(".plus-guest")
let countGuest = valueNumberGuest;
minusGuest.addEventListener('click', () => {
    if (countGuest <= 1) {
        return
    }
    countGuest--;
    countGuest = (countGuest < 10) ? '0' + countGuest : countGuest;
    numberGuest.innerText = countGuest;
})

plusGuest.addEventListener('click', () => {
    if (countGuest >= 20) {
        return
    }
    countGuest++;
    countGuest = (countGuest < 10) ? '0' + countGuest : countGuest;
    numberGuest.innerText = countGuest;
})


const minusRoom = document.querySelector(".minus-room");
let numberRoom = document.querySelector(".num-room")
const plusRoom = document.querySelector(".plus-room")

let countRoom = valueNumberRoom;
minusRoom.addEventListener('click', () => {
    if (countRoom <= 1) {
        return
    }
    countRoom--;
    countRoom = (countRoom < 10) ? '0' + countRoom : countRoom;
    numberRoom.innerText = countRoom;
})

plusRoom.addEventListener('click', () => {
    if (countRoom >= 20) {
        return
    }
    countRoom++;
    countRoom = (countRoom < 10) ? '0' + countRoom : countRoom;
    numberRoom.innerText = countRoom
})


// logic tìm kiếm

const btnSearch = document.getElementById('btn-search');
let inputNameCity = document.getElementById('input-name-city');

btnSearch.addEventListener('click', () => {
    if (!$('#form-search').valid()) return;

    window.location.href = "/danh-sach-khach-san?" +
        "nameCity=" + inputNameCity.value + "&checkIn=" + startDate +
        "&checkOut=" + endDate + "&numberGuest=" + numberGuest.textContent + "&numberRoom=" + numberRoom.textContent;
});

const renderDataSearch = (nameCity, numGuest, numRoom) => {
    if (numGuest < 10) {
        numberGuest.textContent = "0" + numGuest;
    } else {
        numberGuest.textContent = numGuest;
    }
    if (numRoom < 10) {
        numberRoom.textContent = "0" + numRoom;
    } else {
        numberRoom.textContent = numRoom;
    }

    inputNameCity.value = nameCity
    // if (hotel.id === parseInt(btnHeart.value)){
    //     btnHeart.setAttribute("type-button","delete");
    //     btnHeart.classList.add("style-heart")
    // }
}

// khi vừa vào trang thì render ra dữ liệu
window.addEventListener('load', () => {
    renderDataSearch(nameCity, valueNumberGuest, valueNumberRoom);
})

//dóng mở theo bootstrap
const modalReviewConfig = new bootstrap.Modal('#myModal', {
    keyboard: false
})

let idReviewUpdate = null;
let idHotel = null;
// nội dung
const reviewContent = document.getElementById("reviewContent");
const btnSubmitReview = document.getElementById("submitReview");
btnSubmitReview.addEventListener('click', (e) => {
    e.preventDefault();
    if (!$('#form-review').valid()) return;

    if (currentRating === 0) {
        alert("Vui lòng chọn số sao")
        return;
    }
    const dataReview = {
        comment: reviewContent.value,
        rating: currentRating,
        idHotel: idHotel
    }
    console.log(dataReview);
    axios.put("/api/reviews/update/" + idReviewUpdate, dataReview)
        .then((res) => {
            toastr.success("Cập nhật thành công")
            modalReviewConfig.hide();
            const index = reviewLists.findIndex(review => review.id === idReviewUpdate)
            reviewLists[index] = res.data;
            renderReview(reviewLists);
        })
        .catch((err) => {
            toastr.error("Cập nhật thất bại")
        })
})

const renderDataReview = (id) => {
    // Tìm kiếm review theo id
    const review = reviewLists.find(review => review.id === id);
    console.log(review.comment)

    // Cập nhật dữ liệu cho modal
    reviewContent.value = review.comment;

    currentRating = review.rating;
    ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
    highlightStars(currentRating);

    // Lưu lại id review cần cập nhật
    idReviewUpdate = id;

    // lưu lại id hotel
    idHotel = review.hotel.id;
    console.log(idHotel)
}


// Xóa Review
const deleteReview = id => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa review này không?");
    if (!isConfirm) return;
    // Gọi Api để xóa

    axios.delete("/api/reviews/delete/" + id)
        .then((res) => {
            reviewLists = reviewLists.filter(review => review.id !== id)
            renderReview(reviewLists);
            toastr.success("Xóa thành công.");
        })
        .catch((err) => {
            console.log("Thất công")
        })
}


const formatDateShow = (dateString) => {
    const date = new Date(dateString);

    const day = `0${date.getDate()}`.slice(-2); // `05` -> 05 , '015' -> 15
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
};


const reviewListEL = document.querySelector('.container-review-detail')

// hiển thị danh sách review theo khách sạn đang xem
const renderReview = (reviews) => {
    let html = "";
    reviews.forEach(review => {
        html += `<div class="review-detail my-3 ">
                                        <div class="d-flex align-items-end">
                                        
                                            <h4 class="age-user-review p-0 m-0" >${review.rating}/10 - ${review.ratingText} </h4>
                                            <div class="creatAt-review">
                                                ${formatDateShow(review.createAt)}
                                            </div>
                                        </div>
                         
                                        <h6 class="name-user-review">
                                            ${review.user.name}
                                        </h6>
                                       
                                      <div class="wrap-comment">  <span class="content-review">${review.comment}</span></div>
                                        
                                          ${infoUser && infoUser.id === review.user.id ? `
                                             <div class="warp-button-edit" >
                                            <button class=" btn-edit-review btn text-primary" type="button"
                                                    data-bs-toggle="modal" data-bs-target="#myModal" onclick="renderDataReview(${review.id})">Chỉnh sửa
                                            </button>
                                            <button class=" btn-delete-review btn text-danger " onclick="deleteReview(${review.id})" >Xóa</button>
                                               </div>
                                            ` : ''}
                                           </div>`
    });
    reviewListEL.innerHTML = html;
};
// validate  cho  tìm kiếm
$('#form-search').validate({
    rules: {
        nameCity: {
            required: true,
        },
    },
    messages: {
        nameCity: {
            required: "Vui lòng nhập địa chỉ tên thành phố mà bạn muốn tìm kiếm",
        },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});
//  hiển thị  modal chi tiết phòng
const renderImageRoomDetail = async (id) => {
    // lấy ra phòng được chọn để xem chi tiết
    let room = roomList.find(room => room.id === id);
    // gọi api lâấy ra danh sách hinh ảnh của   room đó
    try {
        const res = await axios.get('/api/images/get-image-room/' + id);
        const imageRoomList = res.data;

        // render ra html
        let htmlImage = '';
         imageRoomList.forEach(image => {
            htmlImage += `<div class="item"><img src="${image.url}" alt=""></div>`;
        });
         // truy cập vào thẻ chưa owl ddeeer hiển thị ảnh theo dạng slide
        const sliderMain = document.querySelector('.slider-main');
        sliderMain.innerHTML = htmlImage;
        // Phá hủy Owl Carousel nếu nó đã được khởi tạo
        if ($('.owl-carousel').data('owl.carousel')) {
            $('.owl-carousel').trigger('destroy.owl.carousel');
            $('.owl-carousel').removeClass('owl-loaded');
            $('.owl-carousel').find('.owl-stage-outer').children().unwrap();
        }
        // khởi tạo owl mới
        $('.owl-carousel').owlCarousel({
            loop: true,
            margin: 10,
            nav: true,
            navText: ['<i class="fa-solid fa-angle-left"></i>', '<i class="fa-solid fa-angle-right"></i>'],
            autoHeight: false,
            dots: false,
            responsive: {
                0: {
                    items: 1
                },
                600: {
                    items: 1
                },
                1000: {
                    items: 1
                }
            }
        })
        // gọi hàm render ra thông tin khác của phòng
        renderInfoRoom(room);
    } catch (err) {
        console.error(err);
    }
};

// hàm render ra thông thi  của phòng
const renderInfoRoom =(room)=>{
    document.querySelector('.information-room').innerHTML=`
     <h6 class="name-hotel" >${room.name}</h6>
     <span>${room.description}</span>
     <div class="wrap-icon">
     <span class="icon-amenity">
        <i class="fa-regular fa-square"></i>
     </span>
     <span class="name-amenity-modal" >${room.area}  mét vuông</span>
     </div>
     <div class="wrap-icon">
        <span class="icon-amenity">
            <i class="fa-solid fa-users"></i>
        </span>
        <span class="name-amenity-modal"> ${room.capacity} khách</span>
     </div>
     <div class="wrap-icon">
         <span class="icon-amenity">
              <i class="fa-solid fa-bed"></i>
         </span>
         <span class="name-amenity-modal" > ${room.bedType}</span>
     </div>
    `
    // render ra các tiện ích
    renderAmenityRoom(room);

}


//  hiển thị giá ở việt nam,
const formatPrice = (number ) =>{
   return  number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })
}
// hiển thị tiện ích phòng
const renderAmenityRoom = (room)=>{
    let html = '';
    room.amenityRoomList.forEach(amenity =>{
        html+= `<div class="wrap-amenity-room col-6" >
                                                        <div class="d-flex">
                                                            <i class="fa-solid fa-person-shelter"></i>
                                                            <h6 class="title-amenity-type">${amenity.name}</h6>

                                                        </div>
                                                    </div>`
    })
    document.querySelector('.amenity-room-detail').innerHTML = html;

    document.querySelector('.price-room-modal').innerHTML=`<h5 class="price-room-new">${formatPrice(room.priceAverage)}</h5>`

}


// validate review
$('#form-review').validate({
    rules: {
        content: {
            required: true,
        },
    },
    messages: {
        content: {
            required: "Nội dung không được để trống",
        },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

// xủ lý khi người dùng click vào nút yêu thích
// let btnHeart = document.querySelector('.btn-favourite');

// btnHeart.addEventListener('click',()=>{
//     if (btnHeart.getAttribute("type-button")==="add") {
//         axios.post("/api/hotel/favourite/"+btnHeart.value)
//             .then(()=>{
//                 toastr.success("Đã thêm khách sạn vào danh sách yêu thích.")
//                 btnHeart.classList.add("style-heart")
//                 btnHeart.setAttribute("type-button","delete")
//             })
//             .catch((err)=>{
//                 if (err.response.status===401){
//                     toastr.error("Vui lòng đăng nhập");
//                 }
//             })
//
//     } else {
//         axios.delete("/api/hotel/favourite/"+btnHeart.value)
//             .then(()=>{
//                 toastr.success("Đã xóa khách sạn khỏi danh sách yêu thích.")
//                 btnHeart.classList.remove("style-heart");
//                 btnHeart.setAttribute("type-button","add")
//             })
//             .catch((err)=>{
//                 if (err.response.status===401){
//                     toastr.error("Vui lòng đăng nhập");
//                 }
//             })
//
//     }
// })


renderReview(reviewLists);
