console.log("ShowAllDonations.js loaded ✅");

const cardsContainer = document.getElementById("cards");
const paginationContainer = document.getElementById("pagination");
const searchInput = document.getElementById("searchInput");
const statusFilter = document.getElementById("statusFilter");
const mealFilter = document.getElementById("mealFilter");
const sortBy = document.getElementById("sortBy");

let allCards = [];
let currentPage = 1;
const itemsPerPage = 6;

// Collect cards into array
function initCards() {
  allCards = Array.from(cardsContainer.querySelectorAll(".donation-card"));
  renderCards();
}

// Render filtered, sorted, paginated cards
function renderCards() {
  let filtered = allCards;

  // Search by ID
  const query = searchInput.value.trim().toLowerCase();
  if (query) {
    filtered = filtered.filter(c =>
      (c.dataset.donationId || "").toLowerCase().includes(query)
    );
  }

  // Filter by status
  if (statusFilter.value) {
    filtered = filtered.filter(c => c.dataset.status === statusFilter.value);
  }

  // Filter by meal
  if (mealFilter.value) {
    filtered = filtered.filter(c => c.dataset.mealType === mealFilter.value);
  }

  // Sort
  filtered.sort((a, b) => {
    const da = new Date(a.dataset.claimTime || 0);
    const db = new Date(b.dataset.claimTime || 0);
    return sortBy.value === "oldest" ? da - db : db - da;
  });

  // Pagination
  const totalPages = Math.ceil(filtered.length / itemsPerPage) || 1;
  if (currentPage > totalPages) currentPage = totalPages;
  const start = (currentPage - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  const pageItems = filtered.slice(start, end);

  // Render
  cardsContainer.innerHTML = "";
  pageItems.forEach(c => cardsContainer.appendChild(c));

  // Pagination controls
  paginationContainer.innerHTML = "";
  for (let i = 1; i <= totalPages; i++) {
    const btn = document.createElement("button");
    btn.textContent = i;
    btn.className = "btn btn-sm " + (i === currentPage ? "btn-primary" : "btn-default");
    btn.style.margin = "0 4px";
    btn.onclick = () => { currentPage = i; renderCards(); };
    paginationContainer.appendChild(btn);
  }

  console.log(`Rendering page ${currentPage}/${totalPages}, ${filtered.length} results`);
}

// Event listeners
[searchInput, statusFilter, mealFilter, sortBy].forEach(el =>
  el.addEventListener("input", () => { currentPage = 1; renderCards(); })
);

// Init when DOM ready
document.addEventListener("DOMContentLoaded", initCards);
