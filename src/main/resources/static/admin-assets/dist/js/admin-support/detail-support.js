let titleEl = document.getElementById("title");
let descriptionEl = document.getElementById("description");
let contentEl = document.getElementById("content");
let statusSupportEl = document.getElementById("status");
let supportTypeEl = document.getElementById("support-type");
const saveSupport = document.getElementById("update");

// lấy dữ liệu từ support hiển thị ra
const renderSupport = () => {
    titleEl.value = currentSupport.title;
    descriptionEl.value = currentSupport.description;
    easyMDE.value(currentSupport.content);
    contentEl.value = currentSupport.content;
    supportTypeEl.value = currentSupport.supportType;
    statusSupportEl.value = currentSupport.status ? '1' : '0';
}

// lắng nghe sự kiện của easyMDE
easyMDE.codemirror.on('change', function () {
    contentEl.value = easyMDE.value();
});


let idSupportAdmin = currentSupport.id;
//
saveSupport.addEventListener('click', (e) => {

    const markdownContent = easyMDE.value(); // Lấy nội dung markdown từ EasyMDE
    console.log("Markdown content:", markdownContent);
    e.preventDefault();
    if (!$("#form-support-detail").valid()) return;
    if (contentEl.value === '') {
        alert("Vui lòng nhập nội dung");
        return;
    }
    let status = false;
    if (statusSupportEl.value === "1") {
        status = true;
    }
    const dataSupport = {
        title: titleEl.value,
        description: descriptionEl.value,
        content: contentEl.value,
        supportType: supportTypeEl.value,
        status: status
    }
    axios.put("/api/support/admin/update/" + idSupportAdmin, dataSupport)
        .then((res) => {
            toastr.success("Cập nhật thành công");
            setTimeout(() => {
                window.location.href = "/admin/support";
            }, 1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

})

$('#form-support-detail').validate({
    rules: {
        title: {
            required: true,
        }, content: {
            required: true,
        }, description: {
            required: true
        },
    }, messages: {
        title: {
            required: "Vui lòng nhập tiêu đề bài viết",

        }, content: {
            required: "Vui lòng nhập nội dung của bài viết",

        }, description: {
            required: "Vui lòng nhập mô tả cho bài viết"
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
const deleteSupport = document.getElementById("deleteSupport");
deleteSupport.addEventListener('click', () => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa bài viết này không?");
    if (!isConfirm) return;
    axios.delete("/api/support/admin/delete/" + idSupportAdmin)
        .then((res) => {
            toastr.success("Xóa Thành Công ");
            setTimeout(() => {
                window.location.href = "/admin/support";
            }, 1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

});


renderSupport();