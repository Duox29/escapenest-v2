const logOut = ()=>{
    let isConFirm = confirm("Bạn có chắc rằng muốn đăng xuất?")
    if (isConFirm){
        toastr.success("Đăng xuất thành công");
        setTimeout(()=>{
            window.location.reload();
            window.location.href = "/logout";
        },2000)
    }
}