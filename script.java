function showTab(event, tabId) {
    const contents = document.querySelectorAll('.tab-content');
    contents.forEach(content => content.classList.remove('active-content'));
    const buttons = document.querySelectorAll('.nav-btn');
    buttons.forEach(btn => btn.classList.remove('active'));
    document.getElementById(tabId).classList.add('active-content');
    event.currentTarget.classList.add('active');
}

window.addEventListener('load', () => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getWeather, handleGeoError);
    } else {
        document.getElementById('location').innerText = "Location tracking unsupported";
    }
});

function getWeather(position) {
    const lat = position.coords.latitude;
    const lon = position.coords.longitude;
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

            document.getElementById('location').innerText = `Lat: ${lat.toFixed(2)}, Lon: ${lon.toFixed(2)}`;
            document.getElementById('weather-desc').innerText = description;
            document.getElementById('temperature').innerText = `${temp}°C`;
        })
        .catch(() => {
            document.getElementById('weather-desc').innerText = "Unable to load weather";
        });
}

function handleGeoError() {
    document.getElementById('location').innerText = "Location access denied";
    document.getElementById('weather-desc').innerText = "Enable location to display weather";
      }
          
