let profileDropdownList = document.querySelector(".profile-dropdown-list");
let btn = document.querySelector(".profile-dropdown-btn");

let classList = profileDropdownList.classList;

const toggle = () => classList.toggle("active");

window.addEventListener("click", function (e) {
    if (!btn.contains(e.target)){
        classList.remove("active");
    }
});
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



