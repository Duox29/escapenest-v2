
const proFileMenu = document.querySelectorAll('.profile-menu')

// truy cập vào các node thông tin người dùng để render dữ liêu
const inputName = document.querySelector('.input-name-user');
const inputPhoneNumber = document.querySelector('.input-phone');
const inputAddress = document.querySelector('.address');
const inputMonth = document.querySelectorAll('.month')
const inputDay = document.querySelectorAll('.day');
const inputYear = document.querySelectorAll('.year');
const date = document.getElementById('date-birth-day');
const listGender = document.querySelectorAll('.gender-option');

inputAddress.addEventListener('change' , ()=>{

})


// lấy ngày tháng năm sinh từ các ô option để update dữ liệu
const  getBirthday = () =>{
    let dayBirthday = null;
    inputDay.forEach(optionDay =>{
        if (optionDay.selected === true){
            dayBirthday = optionDay.value
            console.log(dayBirthday)
        }
    })
    let monthBirthday = null;
    inputMonth.forEach(optionMonth =>{
        if (optionMonth.selected === true){
            monthBirthday = optionMonth.value
            console.log(monthBirthday)
        }
    })
    let yearBirthday = null;
    inputYear.forEach(optionYear =>{
        if (optionYear.selected === true){
            yearBirthday = optionYear.value
            console.log(yearBirthday)
        }
    })
    return monthBirthday + '-' + dayBirthday + '-' + yearBirthday;
}


// hiện dữ liệu của người dùng khi vào trang user
const renderData = ()=>{
    let birthdayUser =  new Date( inforUser.birthDay);
    inputName.value = inforUser.name;
    inputPhoneNumber.value = inforUser.phoneNumber;
    inputAddress.value = inforUser.address;
    listGender.forEach((gender) =>{
        if (gender.value === inforUser.gender){
            gender.selected = true
        }
    })

    inputDay.forEach(optionDay =>{
        if (optionDay.value === birthdayUser.getDate().toString()){
            optionDay.selected = true
        }
    })
    inputMonth.forEach(optionMonth =>{
        if (optionMonth.value === (birthdayUser.getMonth()+1).toString()){
            optionMonth.selected = true
        }
    })
    let yearBirthday = null;
    inputYear.forEach(optionYear =>{
        if (optionYear.value === birthdayUser.getFullYear().toString()){
            optionYear.selected = true
        }
    })

}




proFileMenu.forEach(btn =>{
    btn.addEventListener('click',(e)=>{
        e.preventDefault();

        switch (btn.getAttribute('value')){
            case 'profile':
                document.querySelector('.content-setting').classList.remove('d-none');
                document.querySelector('.setting-account').classList.add('d-none');
                break;
            case 'setting':
                document.querySelector('.content-setting').classList.add('d-none');
                document.querySelector('.setting-account').classList.remove('d-none');
                break;
        }

    })
})






// tạo ra form date 00/00/0000
const formatDate = (dateString) => {
    console.log( 'đầu vào'+dateString)
    const date = new Date(dateString);
    console.log( 'tạo date'+date)

    const day = `0${date.getDate()}`.slice(-2); // `05` -> 05 , '015' -> 15
    console.log( 'ngày'+day)
    const month = `0${date.getMonth() + 1}`.slice(-2);
    console.log( 'tháng'+month)

    const year = date.getFullYear();
    console.log( 'năm'+year)

    console.log(`${month}/${day}/${year}`)

    return `${day}/${month}/${year}`;
};

// gọi api khi ấn vào nút lưu , cập nhật dữ liệu cho người dùng
const btnSave = document.querySelector('.btn-save');
btnSave.addEventListener('click' ,()=>{
    if (!$('#input-info-user').valid()) return;

    // lấy ra gender người dùng đang chọn
    let gender = '';
    listGender.forEach((genderEl) =>{
        if (genderEl.selected){
            gender=genderEl.value;
        }
    })

    // lấy ngày sinh nhật người dùng đang  chọn
    const birthdayUser = formatDate(getBirthday());
    const  dataUser ={
        name : inputName.value,
        phone: inputPhoneNumber.value,
        gender : gender,
        address : inputAddress.value,
        birthDay : birthdayUser
    }

    // gọi api cập nhật thông tin người dùng
    axios.put("/api/auth/update-user/"+inforUser.id , dataUser)
        .then((response)=>{
            toastr.success("Đã cập nhật thông tin của bạn")
        })
        .catch((err)=>{
            toastr.error(err.response.data.message)
        })


})

// thay đổi mật khẩu
const btnChangePassword = document.querySelector('.btn-change');

// thay đổi mật khẩu
btnChangePassword.addEventListener('click',()=>{
    if ( !$('#form-change-password').valid()) return;
    const password = document.querySelector('.old-password');
    const newPassword = document.querySelector('.new-password');
    const confirmPassword = document.querySelector('.confirm-password');
    // lấy mật khẩu người dùng đã nhập vào để gửi lên
    const data = {
        oldPassword : password.value,
        newPassword:  newPassword.value,
        confirmPassword:  confirmPassword.value

    }
    axios.put("/api/auth/change-password/"+inforUser.id ,data)
        .then((response) =>{
            modalChangePassword.hide()
            toastr.success("Thay đổi mật khẩu thành công")
        })
        .catch((err)=>{
            toastr.error(err.data.message)
        })
})


const inputImage = document.getElementById('avatar');
const imageModal = document.getElementById('image-user-modal');
const imageUserInformation = document.getElementById('image-user-information');
const saveImage = document.getElementById('saveImage');



// modal thay đổi mật khẩu
const modalChangePassword = new bootstrap.Modal('#changePassword', {
    keyboard: false
})
// modal thay đổi avatar
const modalUploadImage = new bootstrap.Modal('#uploadImage', {
    keyboard: false
})
// // modal thay đổi mật khẩu
// const modalLogout = new bootstrap.Modal('#changePassword', {
//     keyboard: false
// })

let formData = new FormData();
let file = null

// lắng nghe sự kiện khi user upload file
inputImage.addEventListener('input',(e)=>{
    // lấy ra thông tin file upload
    let file = e.target.files[0];
    showImageForElement(file,imageModal)

    // lưu vào đối tượng form data
    formData.delete('file');
    formData.append('file',file);
    saveImage.disabled=false;
})


// lưu ảnh vừa upload lên sever
saveImage.addEventListener('click',(e)=>{
    // gọi api để đẩy dữ liệu ảnh lên
    axios.post('api/images/upload/user',formData)
        .then((response)=>{
            saveImage.disabled=true;
            modalUploadImage.hide();
            imageModal.src=response.data.url
            imageUserInformation.src=response.data.url
            document.querySelector('.img-user-nav-bar').src=response.data.url
            toastr.success("Cập nhật avatar thành công")

        })
        .catch((err)=>{
            toastr.error(err.data.message)
        })

})


const btnDeleteImageUser = document.getElementById('btn-delete-image-user');
// xử lý khi click vaào button xóa image
btnDeleteImageUser.addEventListener('click',(e)=>{
    e.preventDefault();
    // gọi api để xóa dữ liệu ảnh lên
    axios.delete('api/images/delete/user/'+imageUser.id)
        .then((response)=>{
            btnDeleteImageUser.classList.add('d-none')
            modalUploadImage.hide()
            toastr.success("Xóa avatar thành công")
            btnDeleteImageUser.disabled=true;

        })
        .catch((err)=>{
            toastr.error(err.data.message)
        })


})

// show image vừa chọn ra client nhưng chưa được dược lưu
const showImageForElement = (file,elementShow)=>{
    if (file) {
        var reader = new FileReader();
        reader.onload = function(event) {
            var content = event.target.result;
            var fileType = file.type;

            // Kiểm tra nếu tệp tin là hình ảnh
            if (fileType && fileType.startsWith('image')) {
                elementShow.src=content
            } else {

                // Hiển thị nội dung văn bản cho các loại tệp tin khác
                elementShow.innerText = content;
            }
        };
        reader.readAsDataURL(file);
    } else {
        imageModal.innerText = 'Chưa chọn tệp tin.';
    }
}

const showImageOld = () => {
    imageModal.src = imageUser.url;
    formData = new FormData();
}


renderData()