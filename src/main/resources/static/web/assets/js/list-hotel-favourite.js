
const wrapList = document.querySelector('.wrap-list');
let dayStart = new Date();
let dayEnd = new Date(Date.now() + 4 * 24 * 60 * 60 * 1000);
function formatDate (date){
    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng được đánh số từ 0-11, cần +1 và đảm bảo có 2 chữ số
    let day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
// Tạo chuỗi định dạng yyyy-MM-dd
    return year + "-" + month + "-" + day;
}

const renderData = (list) =>{
    let html = '';
    list.forEach(hotelFavourite =>{
        let idHotel = hotelFavourite.id;
        let name=hotelFavourite.city.name;
        let checkIn =formatDate(dayStart);
        let checkout =formatDate(dayEnd);
        let htmlStar = '';
        for (let i = 0; i < hotelFavourite.star ; i++) {
            htmlStar += `<i class="fa-solid fa-star" style="color: #CF2061;"></i>`
        }
        html+=`
                     <div class="col-3 h-100 conatiner-card">         
                            <a class="card-hotel text-reset text-decoration-none w-100" 
                           href="/chi-tiet-khach-san/${idHotel}?nameCity=${name}&checkIn=${checkIn}&checkOut=${checkout}">
                                <img class="image-hotel w-100"  src="${hotelFavourite.poster}" alt="">
                                <div class="infor-hotel h-100">
                                    <p class="name-hotel w-100">${hotelFavourite.name}</p>
                                    <span class="city-hotel" >${hotelFavourite.city.name}</span>
                                    <div class="star">
                                        ${htmlStar}
                                    </div>
                                    <hr>
                                    <div class="ratting-hotel">
                                        <div>
                                            <span class="age-rating" >${hotelFavourite.rating.toFixed(1)}</span>
                                            <span class="rating-text"  > ${hotelFavourite.ratingText}</span>
                                        </div>
                                    </div>
                                    <hr>
                                </div>
                            </a>
                            <div class="delete">
                                <button value="${hotelFavourite.id}" class="btn-delete" type="button"  onclick="deleteHotelFavourite(${hotelFavourite.id})">
                                    <i class="fa-solid fa-xmark"></i>
                                </button>
                            </div>
                        </div>
            `;
    })
    wrapList.innerHTML=html;

}

const deleteHotelFavourite = (id) =>{
    axios.delete("/api/hotel/delete/list-favourite/"+id+"?pageNumber="+currentPage)
        .then((response)=>{
            console.log(response.data.numberOfElements)
            console.log(response)
            let index = response.data.pageable.pageNumber;
            toastr.success("Đã xóa homestay ra khỏi danh sách yêu thích")
            if (response.data.numberOfElements === 0 && index>0){
                window.location.href=window.location.pathname+'?pageNumber='+index
            }
            else if(response.data.numberOfElements === 0 && index===0){
                // document.querySelector('.container-body').innerHTML =
               // window.location.reload();
               console.log(document.querySelector('.title-page'))
               console.log(document.querySelector('.wrap-pagination'))
                document.querySelector('.title-page').innerHTML = `<h1>Chưa có homestay nào được thêm vào mục yêu thích</h1>`
                document.querySelector('.pagination').style.display = 'none';
            }
            hotelList = response.data.content
            renderData(hotelList);
        })
        .catch((err)=>{
            console.log(err)
            toastr.error(err);
        })
}
renderData(hotelList);
