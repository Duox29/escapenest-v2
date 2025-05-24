const nameHotel = document.getElementById('nameHotel');
const descriptionEl = document.getElementById('description');
const cityEl = document.getElementById("city");
const  statusEl = document.getElementById("status")
const btnSave = document.getElementById("btn-Hotel");
const  phoneHotelEl = document.getElementById("phoneHotel");
const emailHotelEl = document.getElementById("emailHotel");
const rentalHotelEl = document.getElementById("rentalType");
const addressHotelEl = document.getElementById("addressHotel");
const starEl = document.getElementById("star");

let idHotel = hotelCurrent.id;
const renderDataHotel = ()=>{
    nameHotel.value=hotelCurrent.name;
    descriptionEl.value=hotelCurrent.description;
    cityEl.value = hotelCurrent.city.id;
    statusEl.value=hotelCurrent.status ? '1' : '0';
    phoneHotelEl.value = hotelCurrent.hotline;
    emailHotelEl.value =hotelCurrent.email;
    rentalHotelEl.value = hotelCurrent.rentalType;
    addressHotelEl.value = hotelCurrent.address;
    starEl.value = hotelCurrent.star
}
btnSave.addEventListener('click' ,(e)=>{
    e.preventDefault();
    if (!$('#form-update-hotel').valid()) return;
    let status = false;
    if (statusEl.value==='1'){
        status=true;
    }
    console.log(parseInt(cityEl.value))
    const data = {
        nameHotel : nameHotel.value,
        descriptionHotel : descriptionEl.value,
        rentalType : rentalHotelEl.value,
        phoneHotel : phoneHotelEl.value,
        status : status,
        emailHotel : emailHotelEl.value,
        addressHotel : addressHotelEl.value,
        idCity :  parseInt(cityEl.value),
        star : parseInt(starEl.value),
    }
    console.log(data)
    axios.put(`/api/hotel/admin/update/${idHotel}`,data)
        .then((res)=>{
            toastr.success("Lưu thông tin thành công ");
            setTimeout(() => {
                window.location.href = "/admin/hotels";
            }, 1500);
        })
        .catch((err)=>{
            console.log(err)
            toastr.error(err.response.data.message);
        })
})
$('#form-update-hotel').validate({
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

// xóa khách sạn
const deleteHotel = document.getElementById("deleteHotel");
deleteHotel.addEventListener('click', () => {
    const isConfirm = confirm("Bạn có chắc mình muốn homestay này không?");
    if (!isConfirm) return;
    console.log(idHotel)
    axios.delete("/api/hotel/admin/delete/" + idHotel)
        .then((res) => {
            toastr.success("Xóa Thành Công ");
            setTimeout(() => {
                window.location.href = "/admin/hotels";
            }, 1500);
        })
        .catch((err) => {
            console.log(err)
            toastr.error(err.response.data.message);
        })

});
renderDataHotel();

































