// assets/js/navbar.js

document.addEventListener("DOMContentLoaded", () => {
    const navbarContainer = document.getElementById("navbar");
    if (!navbarContainer) return; // safety check

    fetch("/navbar.html")
        .then((response) => {
            if (!response.ok) throw new Error("Navbar load failed");
            return response.text();
        })
        .then((data) => {
            navbarContainer.innerHTML = data;

            // Optional: Add behavior after navbar loads (like login button click)
            const loginBtn = document.getElementById("loginBtn");
            if (loginBtn) {
                loginBtn.addEventListener("click", () => {
                    window.location.href = "/login.html";
                });
            }
        })
        .catch((error) => console.error("Error loading navbar:", error));
});
