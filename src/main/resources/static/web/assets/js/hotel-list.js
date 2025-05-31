// hotel-list.js

document.addEventListener("DOMContentLoaded", function () {
    // API endpoint for fetching hotels
    const apiUrl = `http://localhost:9000/api/search/search?nameCity=${encodeURIComponent(nameCity)}&checkIn=${checkIn}&checkOut=${checkOut}&numberGuest=${valueNumberGuest}&numberRoom=${valueNumberRoom}`;
    let allHotels = [];
    let currentSort = 'default';
    let hotelListContainer = null;

    // Function to fetch hotel data
    async function fetchHotels() {
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            allHotels = await response.json();
            updateHotelCount(allHotels.length);
            renderHotels(sortHotels(allHotels));
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
            updateHotelCount(0);
            renderNoHotels();
        }
    }

    // Update hotel count display
    function updateHotelCount(count) {
        const countElement = document.getElementById('hotel-count');
        if (countElement) {
            countElement.textContent = `Tìm thấy ${count} homestay tại ${nameCity}`;
        }
    }

    function sortHotels(hotels) {
        switch(currentSort) {
            case 'name-asc':
                return [...hotels].sort((a, b) => a.name.localeCompare(b.name));
            case 'name-desc':
                return [...hotels].sort((a, b) => b.name.localeCompare(a.name));
            case 'rating-desc':
                return [...hotels].sort((a, b) => b.rating - a.rating);
            case 'rating-asc':
                return [...hotels].sort((a, b) => a.rating - b.rating);
            case 'price-asc':
                return [...hotels].sort((a, b) => a.estimatedPrice - b.estimatedPrice);
            case 'price-desc':
                return [...hotels].sort((a, b) => b.estimatedPrice - a.estimatedPrice);
            default:
                return hotels;
        }
    }

    // Create hotel card element
    function createHotelCard(hotel) {
        const hotelDetailUrl = `/chi-tiet-khach-san/${hotel.id}?nameCity=${encodeURIComponent(nameCity)}&checkIn=${checkIn}&checkOut=${checkOut}&numberGuest=${valueNumberGuest}&numberRoom=${valueNumberRoom}`;

        const colDiv = document.createElement('div');
        colDiv.classList.add('col-md-4', 'mb-4');

        const hotelCard = document.createElement('div');
        hotelCard.classList.add('card', 'h-100', 'shadow-sm', 'hotel-card-clickable');
        hotelCard.style.cursor = 'pointer';
        hotelCard.dataset.hotelId = hotel.id;

        hotelCard.innerHTML = `
            <img src="${hotel.poster}" alt="${hotel.name}" class="card-img-top" style="height: 200px; object-fit: cover;">
            <div class="card-body">
                <h5 class="card-title">${hotel.name}</h5>
                <p class="card-text"><i class="fa-solid fa-location-dot me-1"></i>${hotel.address}</p>
                <div class="d-flex justify-content-between align-items-center mb-2">
                    <div>
                        <span class="badge bg-primary">${hotel.star} <i class="fa-solid fa-star"></i></span>
                        <span class="ms-2">${hotel.ratingText}</span>
                    </div>
                    <span class="text-warning">${hotel.rating > 0 ? hotel.rating : 'N/A'}</span>
                </div>
                <p class="card-text text-danger fw-bold">${hotel.estimatedPrice.toLocaleString()} VND</p>
                <p class="card-text"><small class="text-muted">${hotel.totalReviews} đánh giá</small></p>
            </div>
            <div class="card-footer bg-white">
                <small class="text-muted">Tiện ích: ${hotel.nameAmenity.slice(0, 2).join(', ')}${hotel.nameAmenity.length > 2 ? '...' : ''}</small>
            </div>
        `;

        hotelCard.addEventListener('click', function() {
            window.location.href = hotelDetailUrl;
        });

        colDiv.appendChild(hotelCard);
        return colDiv;
    }

    // Function to render hotels to the page
    function renderHotels(hotels) {
        // Create container if it doesn't exist
        if (!hotelListContainer) {
            hotelListContainer = document.createElement('section');
            hotelListContainer.classList.add('hotel-list-section', 'my-4');

            // Insert before the PR section
            const prSection = document.querySelector('.pr');
            if (prSection) {
                prSection.parentNode.insertBefore(hotelListContainer, prSection);
            } else {
                document.body.appendChild(hotelListContainer);
            }
        }

        // Clear existing content
        hotelListContainer.innerHTML = '';

        // Create container div
        const containerDiv = document.createElement('div');
        containerDiv.classList.add('container');

        // Create row div
        const rowDiv = document.createElement('div');
        rowDiv.classList.add('row');
        rowDiv.id = 'hotel-cards-container';

        // Add heading
        const headingDiv = document.createElement('div');
        headingDiv.classList.add('col-12', 'mb-3');
        headingDiv.innerHTML = `<h3>Danh sách homestay tại ${nameCity}</h3>`;
        rowDiv.appendChild(headingDiv);

        // Add hotel cards
        if (hotels.length === 0) {
            const noResults = document.createElement('div');
            noResults.classList.add('col-12', 'text-center', 'py-5');
            noResults.innerHTML = `
                <h4>Không tìm thấy homestay nào phù hợp</h4>
                <p>Vui lòng thử lại với tiêu chí tìm kiếm khác</p>
            `;
            rowDiv.appendChild(noResults);
        } else {
            hotels.forEach(hotel => {
                rowDiv.appendChild(createHotelCard(hotel));
            });
        }

        containerDiv.appendChild(rowDiv);
        hotelListContainer.appendChild(containerDiv);
    }

    // Show no hotels message
    function renderNoHotels() {
        if (!hotelListContainer) {
            hotelListContainer = document.createElement('section');
            hotelListContainer.classList.add('hotel-list-section', 'my-4');

            const prSection = document.querySelector('.pr');
            if (prSection) {
                prSection.parentNode.insertBefore(hotelListContainer, prSection);
            }
        }

        hotelListContainer.innerHTML = `
            <div class="container">
                <div class="row">
                    <div class="col-12 text-center py-5">
                        <h4>Không tìm thấy homestay nào phù hợp</h4>
                        <p>Vui lòng thử lại với tiêu chí tìm kiếm khác</p>
                    </div>
                </div>
            </div>
        `;
    }

    // Fetch hotels when the page is ready
    fetchHotels();

    // Set initial values in the search form
    document.getElementById('input-name-city').value = nameCity;
    document.getElementById('date-range').value = `${checkIn} - ${checkOut}`;
    document.getElementById('num-guest').textContent = valueNumberGuest.toString().padStart(2, '0');
    document.getElementById('num-room').textContent = valueNumberRoom.toString().padStart(2, '0');

    // Add event listeners for guest and room count buttons
    document.getElementById('plus-guest').addEventListener('click', function() {
        let numGuest = parseInt(document.getElementById('num-guest').textContent);
        if (numGuest < 10) {
            document.getElementById('num-guest').textContent = (numGuest + 1).toString().padStart(2, '0');
        }
    });

    document.getElementById('minus-guest').addEventListener('click', function() {
        let numGuest = parseInt(document.getElementById('num-guest').textContent);
        if (numGuest > 1) {
            document.getElementById('num-guest').textContent = (numGuest - 1).toString().padStart(2, '0');
        }
    });

    document.getElementById('plus-room').addEventListener('click', function() {
        let numRoom = parseInt(document.getElementById('num-room').textContent);
        if (numRoom < 10) {
            document.getElementById('num-room').textContent = (numRoom + 1).toString().padStart(2, '0');
        }
    });

    document.getElementById('minus-room').addEventListener('click', function() {
        let numRoom = parseInt(document.getElementById('num-room').textContent);
        if (numRoom > 1) {
            document.getElementById('num-room').textContent = (numRoom - 1).toString().padStart(2, '0');
        }
    });

    // Add search button functionality
    document.getElementById('btn-search').addEventListener('click', function() {
        const nameCity = document.getElementById('input-name-city').value;
        const dateRange = document.getElementById('date-range').value.split(' - ');
        const checkIn = dateRange[0];
        const checkOut = dateRange[1];
        const numberGuest = document.getElementById('num-guest').textContent;
        const numberRoom = document.getElementById('num-room') ? document.getElementById('num-room').textContent : '01';

        window.location.href = `/danh-sach-khach-san?nameCity=${encodeURIComponent(nameCity)}&checkIn=${checkIn}&checkOut=${checkOut}&numberGuest=${numberGuest}&numberRoom=${numberRoom}`;
    });

    // Add event listeners for sorting options
    document.querySelectorAll('.sort-option').forEach(option => {
        option.addEventListener('click', function(e) {
            e.preventDefault();
            currentSort = this.dataset.sort;

            // Update the dropdown button text
            document.getElementById('sortDropdown').textContent = this.textContent;

            // Re-render the hotels with the new sort
            renderHotels(sortHotels(allHotels));
        });
    });
});