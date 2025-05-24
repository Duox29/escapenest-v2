$(function() {
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

    // Get checkIn and checkOut values from URL
    const checkInValue = getUrlParameter('checkIn');
    const checkOutValue = getUrlParameter('checkOut');

    // Set default values if not found in URL
    const startDate = checkInValue ? checkInValue : moment().format('YYYY-MM-DD');
    const endDate = checkOutValue ? checkOutValue : moment().add(1, 'days').format('YYYY-MM-DD');
    // Khởi tạo daterangepicker với giá trị từ backend
    $('#date-range').daterangepicker({
        opens: 'left',
        startDate: startDate,
        endDate: endDate,
        minDate: new Date(),
        locale: {
            format: 'YYYY-MM-DD'
        }
    }, function(start, end) {
        console.log("New date range selected: " + start.format('YYYY-MM-DD') + " to " + end.format('YYYY-MM-DD'));
    });
});

// hotel-list.js

document.addEventListener("DOMContentLoaded", function () {
    // API endpoint for fetching hotels
    const apiUrl = `http://localhost:9000/api/search/search?nameCity=${encodeURIComponent(nameCity)}&checkIn=${checkIn}&checkOut=${checkOut}&numberGuest=${valueNumberGuest}&numberRoom=${valueNumberRoom}`;

    // Function to fetch hotel data
    async function fetchHotels() {
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const hotels = await response.json();
            renderHotels(hotels);
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
    }

    // Function to render hotels to the page
    function renderHotels(hotels) {
        // Create a container for the hotel list section
        const hotelListSection = document.createElement('section');
        hotelListSection.classList.add('hotel-list-section', 'my-4');

        // Create a container div
        const containerDiv = document.createElement('div');
        containerDiv.classList.add('container');

        // Create a row div
        const rowDiv = document.createElement('div');
        rowDiv.classList.add('row');

        // Add a heading for the section
        const headingDiv = document.createElement('div');
        headingDiv.classList.add('col-12', 'mb-3');
        headingDiv.innerHTML = `<h3>Danh sách homestay tại ${nameCity}</h3>`;
        rowDiv.appendChild(headingDiv);

        // Loop through hotels and create cards
        hotels.forEach(hotel => {
            const hotelDetailUrl = `/chi-tiet-khach-san/${hotel.id}?nameCity=${encodeURIComponent(nameCity)}&checkIn=${checkIn}&checkOut=${checkOut}&numberGuest=${valueNumberGuest}&numberRoom=${valueNumberRoom}`;
            const colDiv = document.createElement('div');
            colDiv.classList.add('col-md-4', 'mb-4');

            const hotelCard = document.createElement('div');
            hotelCard.classList.add('card', 'h-100', 'shadow-sm','hotel-card-clickable');
            hotelCard.style.cursor = 'pointer';
            hotelCard.addEventListener('click',function (){
                window.location.href = hotelDetailUrl;
            });

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

            colDiv.appendChild(hotelCard);
            rowDiv.appendChild(colDiv);
        });

        containerDiv.appendChild(rowDiv);
        hotelListSection.appendChild(containerDiv);

        // Find the correct location to insert the hotel list
        const prSection = document.querySelector('.pr');
        if (prSection) {
            prSection.parentNode.insertBefore(hotelListSection, prSection);
        } else {
            // Fallback: append to body if the PR section is not found
            document.body.appendChild(hotelListSection);
            console.warn('PR section not found, appending to body instead.');
        }
    }

    // Fetch hotels when the page is ready
    fetchHotels();

    // Initialize date range picker
    $(function() {
        $('input[name="daterange"]').daterangepicker({
            opens: 'left',
            locale: {
                format: 'YYYY-MM-DD'
            }
        });
    });

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
        let numGuest = parseInt(document.getElementById('num-room').textContent);
        if (numGuest < 10) {
            document.getElementById('num-room').textContent = (numGuest + 1).toString().padStart(2, '0');
        }
    });

    document.getElementById('minus-room').addEventListener('click', function() {
        let numGuest = parseInt(document.getElementById('num-room').textContent);
        if (numGuest > 1) {
            document.getElementById('num-room').textContent = (numGuest - 1).toString().padStart(2, '0');
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
});
