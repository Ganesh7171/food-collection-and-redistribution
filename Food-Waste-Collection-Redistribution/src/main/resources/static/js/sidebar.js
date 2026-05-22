function toggleNav() {
    const sideNav = document.getElementById("sideNav");
    if (sideNav.classList.contains("open")) {
        sideNav.classList.remove("open");
    } else {
        sideNav.classList.add("open");
    }
}
function toggleNav() {
    const sideNav = document.getElementById("sideNav");
    sideNav.classList.toggle("open");
}

// Optional: close sidebar when clicking outside
document.addEventListener("click", function(event) {
    const sideNav = document.getElementById("sideNav");
    const hamburger = document.querySelector(".hamburger-btn");

    if (!sideNav.contains(event.target) && !hamburger.contains(event.target)) {
        sideNav.classList.remove("open");
    }
});



// Close on Escape key
document.addEventListener("keydown", (e) => {
  if (e.key === "Escape") {
    closeNav();
  }
});

function openUserModal(userId) {
    document.getElementById('userModal'+userId).style.display = 'block';
}

function closeModal(span) {
    span.closest('.modal').style.display = 'none';
}

// Close modal if click outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = 'none';
    }
}

