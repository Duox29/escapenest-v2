const btnCreateGenre =document.getElementById("btn-create-genre");
let titleModal = document.getElementById("modal-title");
let nameGenre = document.getElementById("nameGenre");
let btnSaveGenre = document.getElementById("save-genre");
let idGenre = null;


btnSaveGenre.addEventListener('click',()=>{

    if (!$("#genre").valid()){
        return;
    }
    if (idGenre==null){
        functionCreateGenre();
    }else {
        functionUpdateGenre(idGenre)
    }
})

const uploadGenre = (idge)=>{
    idGenre = idge;
    const currentGenre = genreList.find(gen => gen.id===idGenre);
    nameGenre.value= currentGenre.name;
    $('#modal-genre').modal('show')
    titleModal.textContent="Chỉnh sửa thể loại";
}
const deleteGenre = (idGe)=>{
    let isConfirm  = confirm("Bạn có chắc muốn xóa thể loại này không")
    if (!isConfirm)return;
    axios.delete(`/api/admin/genre/delete/${idGe}`)
        .then((res)=>{
            toastr.success("Xóa thành công ");
            setTimeout(()=>{
                window.location.reload()
            },1500)
        })
        .catch((er)=>{
            console.log(er)
            toastr.error("Xóa thất bại");
        })
}

btnCreateGenre.addEventListener('click' ,() =>{
    $('#modal-genre').modal('show')
    titleModal.textContent="Tạo thể loại mới";
    nameGenre.value='';
} )


const functionCreateGenre = ()=>{
    let isConf = true;
    genreList.forEach(gen =>{
        if (gen.name===nameGenre.value){
            toastr.error("Thể loại đã tồn tại  ");
            isConf=false;
        }
    })
    if (!isConf){
        return;
    }
    const data={
        name:nameGenre.value
    }
    axios.post("/api/admin/genre",data)
        .then(()=>{
            toastr.success("Lưu thành công ");

            setTimeout(()=>{
                window.location.reload()
            },1500)
        })
        .catch(()=>{
            toastr.error("Tạo mới không thành công");
            $('#modal-genre').modal('hidden')
        })
}
const  functionUpdateGenre = (idGe)=>{
    let isConf = true;
    genreList.forEach(gen =>{
        if (gen.name===nameGenre.value){
            toastr.error("Thể loại đã tồn tại  ");
            isConf=false;
        }
    })
    if (!isConf){
        return;
    }
    const data={
        name:nameGenre.value
    }
    axios.put(`/api/admin/genre/update/${idGe}`,data)
        .then(()=>{
            toastr.success("Lưu thành công ");

            setTimeout(()=>{
                window.location.reload()
            },1500)
        })
        .catch(()=>{
            toastr.error("Update thât bại");
            $('#modal-genre').modal('hidden')
        })
}


$("#genre").validate({
    rules: {
        nameGenre: {
            required: true,
        },
    },
    messages: {
        nameGenre: {
            required: "Vui lòng nhập tên thể loại",
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