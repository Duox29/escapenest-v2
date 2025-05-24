const btnBooking = document.querySelector('.verify-booking');
const nameCustomer = document.querySelector('.name-customer-value');
const emailCustomer = document.querySelector('.email-customer-value');
const phoneCustomer = document.querySelector('.phone-customer-value');
const numGuestInput = document.querySelector('#numGuestInput');
const numRoomInput = document.querySelector('#numRoomInput');

// Hàm kiểm tra validate cho tất cả các trường
function validateForm() {
    let isValid = true;

    // Validate form info-customer
    if (!$('#info-customer').valid()) {
        isValid = false;
    }

    // Validate numGuestInput
    const guestValue = parseInt(numGuestInput.value);
    if (!numGuestInput.value || isNaN(guestValue) || guestValue < 1 || guestValue > 20) {
        toastr.error('Số người phải là số nguyên từ 1 đến 20.');
        isValid = false;
    }

    // Validate numRoomInput
    const roomValue = parseInt(numRoomInput.value);
    if (!numRoomInput.value || isNaN(roomValue) || roomValue < 1 || roomValue > 20) {
        toastr.error('Số phòng phải là số nguyên từ 1 đến 20.');
        isValid = false;
    }

    return isValid;
}

// Gọi API để tạo 1 booking
btnBooking.addEventListener('click', () => {
    if (!validateForm()) {
        return; // Dừng nếu validation thất bại
    }

    const paymentHotel = document.querySelector('input[name="payment"]:checked');
    if (!paymentHotel) {
        toastr.error('Vui lòng chọn hình thức thanh toán.');
        return;
    }

    // Lấy giá trị từ input số người và số phòng
    const guest = parseInt(numGuestInput.value) || numGuest;
    const numberRoom = parseInt(numRoomInput.value) || numRoom;

    const data = {
        idHotel: idHotel,
        idRoom: idRoom,
        nameCustomer: nameCustomer.value,
        emailCustomer: emailCustomer.value,
        phoneCustomer: phoneCustomer.value,
        guest: guest,
        numberRoom: numberRoom,
        checkIn: window.startDate,
        checkOut: window.endDate,
        price: totalPrice,
        paymentMethod: paymentHotel.value
    };
    console.log(data);

    if (paymentHotel.value === "VN_PAY") {
        axios.post("/api/payment/vn-pay", data)
            .then((response) => {
                console.log(response);
                window.location.href = response.data.url;
            })
            .catch((error) => {
                console.log(error);
                toastr.error(error.response.data.message || 'Đã xảy ra lỗi khi thanh toán.');
            });
    } else {
        axios.post("/api/booking/add", data)
            .then((response) => {
                toastr.success("Đặt phòng thành công");
            })
            .catch((error) => {
                console.log(error);
                toastr.error(error.response.data.message || 'Đã xảy ra lỗi khi đặt phòng.');
            });
    }
});

$('#info-customer').validate({
    rules: {
        nameCustomer: {
            required: true,
        },
        emailCustomer: {
            required: true,
            email: true
        },
        phoneCustomer: {
            required: true
        },
    },
    messages: {
        nameCustomer: {
            required: "Vui lòng nhập tên người lưu trú.",
        },
        emailCustomer: {
            required: "Vui lòng nhập email.",
            email: "Email không đúng định dạng."
        },
        phoneCustomer: {
            required: "Vui lòng nhập số điện thoại của người lưu trú.",
        },
    },
    errorElement: 'span',
    errorPlacement: function(error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function(element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function(element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

// Đảm bảo toastr được tải và cấu hình (nếu chưa có)
if (typeof toastr !== 'undefined') {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
} else {
    console.error('toastr is not loaded. Please include toastr library.');
}