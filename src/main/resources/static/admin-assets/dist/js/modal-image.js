const inputImage = document.getElementById("avatar");
const btnChoseImage = document.getElementById("btn-chose-image");
const btnDeleteImage = document.getElementById("btn-delete-image");
let imageContainer = document.querySelector(".image-container");
const thumbnailPreview = document.getElementById("thumbnail");

const renderImage = images => {
    let htmlImage = "";
    images.forEach(image => {
        htmlImage += `<div class="image-item" onclick="choseImage(this)" data-id="${image.id}">
                                <img src="${image.url}"
                                     alt="">
                       </div>`;
    });
    imageContainer.innerHTML = htmlImage;
}
inputImage.addEventListener('change' ,(e)=>{
    // lấy ra thông tin file mà người ta upload
    const file = e.target.files[0];
    // tạo đối tượng
    const formData = new FormData();
    formData.append("file",file);
    axios.post("/api/admin/images",formData)
        .then((res)=>{
            imageList.unshift(res.data);
            renderImage(imageList);
            renderPagination(imageList);
            toastr.success("Upload ảnh thành công ");

        })
        .catch((err)=>{
            console.log(err);
        })

})
const choseImage = (el) =>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (selectedEl){
        selectedEl.classList.remove("selected");
    }
    el.classList.add("selected")
    btnChoseImage.disabled=false;
    btnDeleteImage.disabled = false;

}
// chọn ảnh
btnChoseImage.addEventListener('click',()=>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl){
        toastr.error("Vui lòng chọn ảnh cần xóa ");
    }
    let urlImage= selectedEl.querySelector("img").getAttribute("src");
    // thumbnailPreview.setAttribute("src",urlImage);
    thumbnailPreview.src = urlImage;
    $('#modal-image').modal('hide');

})
// xóa ảnh
btnDeleteImage.addEventListener('click',()=>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl){
        toastr.error("Vui lòng chọn ảnh cần xóa ");
    }
    const imageId= selectedEl.getAttribute("data-id");
    axios.delete(`/api/admin/images/${imageId}`)
        .then((res)=>{
            imageList = imageList.filter(image => image.id!==imageId);
            renderImage(imageList)
            renderPagination(imageList);
            btnChoseImage.disabled=true;
            btnDeleteImage.disabled = true;
            toastr.success("Xóa ảnh thành công ");

        })
        .catch((err)=>{
            console.log(err);
        })

})
const  renderPagination = (images) =>{
    $('#image-pagination').pagination({
        dataSource:images,
        pageSize: 12,
        pageRange: null,
        showPageNumbers: true,
        callback: function(data, pagination) {
            // template method of yourself
            renderImage(data);
        }
    })
}
renderPagination(imageList);


