const  formLogin = document.querySelector('.form-login');

const showPassword = document.querySelector('.show-password');
const inputEmail = document.getElementById('email');
const inputPassword = document.getElementById('password');
showPassword.addEventListener('click',()=>{
    if (inputPassword.getAttribute('type') === 'password') {
        inputPassword.setAttribute('type', 'text');
        showPassword.innerHTML=`<i class="fa-regular fa-eye-slash"></i>`

    } else {
        inputPassword.setAttribute('type', 'password');
        showPassword.innerHTML=`<i  class="fa-regular fa-eye"></i>`
    }
})

// gửi thông tin người đăng nhập serverr
formLogin.addEventListener('submit', (e)=>{
    if (!$('#form-login').valid()){

        return;
    }

    e.preventDefault();
    const data = {
        email :inputEmail.value,
        password : inputPassword.value
    }
    console.log(data)
    axios.post("/api/auth/login",data)
        .then((res) =>{
            toastr.success("Đăng nhập thành công.")
            setTimeout(()=>{
                window.location.href = "http://localhost:9000/";
            },3000)

        })
        .catch((er)=>{
            toastr.error(er.response.data.message)
        })

} )

// quên mật khẩu
const btnForgotPassword = document.querySelector('.btn-forgot-password');
const inputForgotPassword = document.querySelector('.input-email-forgot');
// gọi api để xác nhận quên mật khẩu
btnForgotPassword.addEventListener('click',()=>{
    axios.post("/api/auth/forgot-password?email="+inputForgotPassword.value)
        .then((response)=>{
           toastr.success("Vui lòng xác nhận qua email")
        })
        .catch((err)=>{
            toastr.error(err.response.data.message)
        })
})
// validate dữ liệu
$('#form-login').validate({
    rules: {
        email: {
            required: true,
            email:true
        },
        password: {
            required: true,
            minlength: 6,
        },
    },
    messages: {
        email: {
            required: "Email không được để trống",
            email:"Email không đúng định dạng"
        },
        password: {
            required: "Mật khẩu không được để trống",
            minlength: "Mật khẩu phải có ít nhất 6 ký tự.",
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