const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const btnRegister = document.querySelector(".btn-register");

const formRegister = document.getElementById('form-register');
const showPassword = document.querySelector('.show-password');
const showPasswordConfirm = document.querySelector('.show-password-confirm');
const inputPassword = document.querySelector(".password");
const inputConfirmPassword = document.querySelector(".confirm-password");


// <Hiển thị mật khẩu>
showPassword.addEventListener('click',()=>{
    if (inputPassword.getAttribute('type') === 'password') {
        inputPassword.setAttribute('type', 'text');
        showPassword.innerHTML=`<i class="fa-regular fa-eye-slash"></i>`

    } else {
        inputPassword.setAttribute('type', 'password');
        showPassword.innerHTML=`<i  class="fa-regular fa-eye"></i>`
    }
})
showPasswordConfirm.addEventListener('click',()=>{
    if (inputConfirmPassword.getAttribute('type') === 'password') {
        inputConfirmPassword.setAttribute('type', 'text');
        showPasswordConfirm.innerHTML=`<i class="fa-regular fa-eye-slash"></i>`

    } else {
        inputConfirmPassword.setAttribute('type', 'password');
        showPasswordConfirm.innerHTML=`<i  class="fa-regular fa-eye"></i>`
    }
})



// gửi thông tin đăng ký lên server
formRegister.addEventListener('submit' ,(e)=>{
    console.log("adbb")
    e.preventDefault();
    if (!$('#form-register').valid()) return;
    const data = {
        name:inputName.value,
        email : inputEmail.value,
        password: inputPassword.value,
        confirmPassword: confirmPassword.value
    }
    console.log("adbb")
    axios.post("/api/auth/register",data)
        .then((res) =>{
            toastr.success("Đăng ký thành công. Vui lòng kiểm tra email xác thực tài khoản ")
        })
        .catch((er)=>{
            toastr.error(er.response.data.message);
        })
})

$('#form-register').validate({
    rules: {
        nameUser: {
            required: true,
        },
        email: {
            required: true,
            email:true
        },
        inputPassword: {
            required: true,
            minlength: 6,
            // pattern: /^(?=.*[A-Za-z])(?=.*\d).{6,}$/, // ít nhất một chữ cái và một số, ít nhất 6 ký tự
        },
        confirmPassword: {
            required: true,
            minlength: 6,
            equalTo: "[name='inputPassword']"
        },
    },
    messages: {
        nameUser: {
            required: "Vui lòng nhập tên của bạn",
        },
        email: {
            required: "Vui lòng email của bạn",
            email:"Email không đúng định dạng"
        },
        inputPassword: {
            required: "Vui lòng nhập mật khẩu",
            minlength: "Mật khẩu phải có ít nhất 6 ký tự.",
            pattern: "Mật khẩu mới phải chứa ít nhất một chữ cái và một số.",
        },
        confirmPassword: {
            required: "Vui lòng nhập tên của bạn",
            minlength: "Mật khẩu phải có ít nhất 6 ký tự.",
            equalTo: "Mật khẩu không trùng khớp",
        },
    },
    errorElement: 'label',
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