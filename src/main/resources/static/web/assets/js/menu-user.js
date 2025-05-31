// Check if elements exist before setting up event listeners
const profileDropdownList = document.querySelector(".profile-dropdown-list");
const btn = document.querySelector(".profile-dropdown-btn");

if (profileDropdownList && btn) {
    const classList = profileDropdownList.classList;

    const toggle = () => classList.toggle("active");
    btn.addEventListener('click', toggle);

    window.addEventListener("click", function (e) {
        if (!btn.contains(e.target) && !profileDropdownList.contains(e.target)) {
            classList.remove("active");
        }
    });
}

const logOut = () => {
    let isConFirm = confirm("Bạn có chắc rằng muốn đăng xuất?")
    if (isConFirm) {
        toastr.success("Đăng xuất thành công");
        setTimeout(() => {
            window.location.href = "/logout";
        }, 2000)
    }
}