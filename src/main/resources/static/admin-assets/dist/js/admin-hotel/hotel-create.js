const nameHotel = document.getElementById('nameHotel');
const descriptionEl = document.getElementById('description');
const cityEl = document.getElementById("city");
const  statusEl = document.getElementById("status")
const btnSave = document.getElementById("btn-create-hotel");
const  phoneHotelEl = document.getElementById("phoneHotel");
const emailHotelEl = document.getElementById("emailHotel");
const rentalHotelEl = document.getElementById("rentalType");
const starEl = document.getElementById("star");
const addressHotelEl = document.getElementById("addressHotel");


// tạo khách sạn mới
btnSave.addEventListener('click' ,(e)=>{
    e.preventDefault();
    if (!$('#form-create-hotel').valid()) return;
    let status = false;
    if (statusEl.getAttribute("value")==="true"){
        status=true;
    }
    const data = {
        name : nameHotel.value,
        description : descriptionEl.value,
        rentalType : rentalHotelEl.value,
        phoneHotel : phoneHotelEl.value,
        status : status,
        email : emailHotelEl.value,
        addressHotel : addressHotelEl.value,
        idCity :  parseInt(cityEl.value),
        star : parseInt(starEl.value)
    }
    console.log(data);
    axios.post("/api/hotel/admin/create",data)
        .then((res)=>{
            toastr.success("Tạo homestay mới thành công");
            setTimeout(() => {
                window.location.href = "/admin/hotels";
            }, 1500);
        })
        .catch((err)=>{
           console.log(err)
        })
})


$('#form-create-hotel').validate({
    rules: {
        nameHotel: {
            required: true,
        },
        city: {
            required: true,
        },
        description: {
            required: true
        },
        address: {
            required: true
        },
        phoneHotel: {
            required: true
        },
        emailHotel: {
            required: true,
            email : true
        },

    }, messages: {
        nameHotel: {
            required: "Vui lòng nhập tên homestay",

        }, city: {
            required: "Vui lòng chọn thành phố ",

        }, description: {
            required: "Vui lòng thêm mô tả"
        }
        , address: {
            required: "Vui lòng nhập địa chỉ cho homestay"
        }
        , phoneHotel: {
            required: "Vui lòng nhập số điện thoại"
        }
        , emailHotel: {
            required: "Vui lòng nhập email"
        }
    }, errorElement: 'span', errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }, highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    }, unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});




































