// Toggle Functionality System
function showTab(event, tabId) {
    const contentCard = document.getElementById('contentBox');
    
    // Check if the clicked button is already active
    if (event.currentTarget.classList.contains('active')) {
        // Close everything if pressed a second time (Exit/Collapse behavior)
        event.currentTarget.classList.remove('active');
        document.getElementById(tabId).classList.remove('active-content');
        contentCard.classList.remove('revealed');
        return;
    }

    // Otherwise, ensure the main card structure is visible
    contentCard.classList.add('revealed');

    // Hide all present section layout panels
    const contents = document.querySelectorAll('.tab-content');
    contents.forEach(content => content.classList.remove('active-content'));
    
    // Drop active selection state from all button elements
    const buttons = document.querySelectorAll('.nav-btn');
    buttons.forEach(btn => btn.classList.remove('active'));
    
    // Instantly reveal target chosen panel and attach active button focus styles
    document.getElementById(tabId).classList.add('active-content');
    event.currentTarget.add('active');
}

// Automatically request phone geolocation metrics
window.addEventListener('load', () => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getWeather, handleGeoFallback, {
            timeout: 3000,
            enableHighAccuracy: false
        });
    } else {
        handleGeoFallback();
    }
});

function getWeather(position) {
    fetchWeatherData(position.coords.latitude, position.coords.longitude, `Lat: ${position.coords.latitude.toFixed(2)}, Lon: ${position.coords.longitude.toFixed(2)}`);
}

function handleGeoFallback() {
    fetchWeatherData(-26.20, 28.04, "Johannesburg South (Default Base)");
}

function fetchWeatherData(lat, lon, locationLabel) {
    const apiURL = `https://open-meteo.com{lat}&longitude=${lon}&current_weather=true`;

    fetch(apiURL)
        .then(response => response.json())
        .then(data => {
            const temp = data.current_weather.temperature;
            const weatherCode = data.current_weather.weathercode;
            
            let description = "Clear skies";
            if (weatherCode > 0 && weatherCode <= 3) description = "Partly Cloudy";
            else if (weatherCode >= 45 && weatherCode <= 48) description = "Foggy conditions";
            else if (weatherCode >= 51 && weatherCode <= 67) description = "Drizzle / Rain";
            else if (weatherCode >= 71) description = "Stormy weather";

            document.getElementById('location').innerText = locationLabel;
            document.getElementById('weather-desc').innerText = description;
            document.getElementById('temperature').innerText = `${temp}°C`;
        })
        .catch(() => {
            document.getElementById('weather-desc').innerText = "Weather monitoring offline";
        });
}
