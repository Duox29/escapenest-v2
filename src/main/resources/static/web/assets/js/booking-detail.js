const cancelBooking = document.querySelector('.cancel-booking');
const btnReview = document.querySelector('.create-review');
cancelBooking.addEventListener('click' , ()=>{
    window.location.href="/cancel-booking/"+cancelBooking.value;
})

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

//dóng mở theo bootstrap
const modalReviewConfig = new bootstrap.Modal('#myModal', {
    keyboard: false
})

// nội dung
const reviewContent = document.getElementById("reviewContent");

const btnSubmitReview = document.getElementById("submitReview");
btnSubmitReview.addEventListener('click', () => {

    // kiểm tra xem đã nhập nội dung chưa
    if (!$("#form-review").valid()) return;


    if (currentRating === 0) {
        alert("Vui lòng chọn số sao")
        return;
    }
    const dataReview = {
        comment: reviewContent.value,
        rating: currentRating,
        idBooking: btnReview.value
    }

    console.log(dataReview);
    axios.post("/api/reviews/create", dataReview)
        .then((res) => {
           toastr.success("Gửi đánh giá thành công");
           document.querySelector('.wrap-btn-reviews').innerHTML='';
            modalReviewConfig.hide();
        })
        .catch((err) => {
            console.log(err);
        })

})

$('#form-review').validate({
    rules: {
        contentReview: {
            required: true,
        },
    },
    messages: {
        contentReview: {
            required: "Vui lòng nhập nội dung",
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
