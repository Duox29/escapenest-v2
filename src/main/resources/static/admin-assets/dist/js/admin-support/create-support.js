
const titleSupportEl = document.getElementById("title");
let contentSupportEl = document.getElementById("content");
const  descriptionSupportEl = document.getElementById("description");
const saveSupport= document.getElementById("save");
const statusSupportEl = document.getElementById("status");
let supportTypeEl = document.getElementById("support-type");

easyMDE.codemirror.on('change', function () {
    contentSupportEl.value = easyMDE.value();
});
// tạo bài viết
saveSupport.addEventListener('click',(e)=>{
    e.preventDefault();
    if (!$("#form-support").valid()) return;
    let status = false ;
    if (statusSupportEl.value==="1"){
        status = true;
    }
    const dataSupport = {
        title : titleSupportEl.value,
        description : descriptionSupportEl.value,
        content : contentSupportEl.value,
        status : status,
        supportType : supportTypeEl.value
    }
    axios.post("/api/support/admin/create", dataSupport)
        .then((res) => {
            const  id = res.data.id;
            toastr.success("Tạo bài viết thành công ");
            setTimeout(()=>{
                window.location.href="/admin/support/"+id+"/detail";
            },1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

})

$('#form-support').validate({
    rules: {
        title: {
            required: true,
        },
        content: {
            required: true,
        },
        description: {
            required: true
        },
    },
    messages: {
        title: {
            required: "Vui lòng nhập tiêu đề bài viết",

        },
        content: {
            required: "Vui lòng nhập nội dung của bài viết",

        },
        description: {
            required : "Vui lòng nhập mô tả cho bài viết"
        }
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



