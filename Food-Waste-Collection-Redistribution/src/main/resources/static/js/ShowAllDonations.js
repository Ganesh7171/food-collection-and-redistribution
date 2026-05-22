console.log("ShowAllDonations.js loaded ✅");

/* ==========================
   Sidebar Toggle
========================== */
function toggleNav() {
  const nav = document.getElementById("sideNav");
  const content = document.getElementById("pageContent");
  const scrim = document.getElementById("scrim");

  const isOpen = nav.classList.toggle("open");
  if (content) content.classList.toggle("shifted", isOpen);
  if (scrim) scrim.classList.toggle("show", isOpen);
}

// Close sidebar on ESC
document.addEventListener("keydown", function (e) {
  if (e.key === "Escape") {
    const nav = document.getElementById("sideNav");
    if (nav.classList.contains("open")) toggleNav();
  }
});

/* ==========================
   Claim Modal
========================== */
function openClaimPopup(donationId) {
  document.getElementById("donationIdField").value = donationId;
  $("#claimModal").modal("show");
}

/* ==========================
   Details Panel
========================== */
function showDetailsFromCard(cardEl) {
  const d = cardEl.dataset;

  const username = d.username && d.username !== "null" ? d.username : "N/A";
  const mobile = d.mobile && d.mobile !== "null" ? d.mobile : "N/A";
  const address = d.address && d.address !== "null" ? d.address : "N/A";
  const gpc =
    d.googlePlusCode && d.googlePlusCode !== "null" ? d.googlePlusCode : "N/A";

  // Action button logic
  let buttonHtml = "";
  if (d.status === "CLAIMED") {
    buttonHtml = `
      <button class="modern-btn disabled-btn" disabled>
        <i class="fas fa-check-circle"></i> Request Already Placed
      </button>`;
  } else {
    buttonHtml = `
      <button class="modern-btn primary-btn"
              type="button"
              onclick="event.stopPropagation();openClaimPopup('${d.donationId}')">
        <i class="fas fa-hand-paper"></i> Place Request
      </button>`;
  }

  // Build HTML
  const html = `
    <h3 class="details-title">Donation #${d.donationId}</h3>
    <div class="details-grid">
      <div class="row-item"><div class="rk">Meal</div><div class="rv">${d.mealType || "-"}</div></div>
      <div class="row-item"><div class="rk">Status</div><div class="rv">${d.status}</div></div>
      <div class="row-item"><div class="rk">Claim Time</div><div class="rv">${d.claimTime || "-"}</div></div>
      <div class="row-item"><div class="rk">Address</div><div class="rv">${address}</div></div>
      <div class="row-item">
        <div class="rk">Google Plus Code</div>
        <div class="rv">
          ${gpc}
          ${
            gpc && gpc !== "N/A"
              ? `<a class="map-link" target="_blank"
                   href="https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
                     gpc
                   )}">
                  View on Map
                 </a>`
              : ""
          }
        </div>
      </div>
      <div class="row-item"><div class="rk">User</div><div class="rv">${username}</div></div>
      <div class="row-item"><div class="rk">Mobile</div><div class="rv">${mobile}</div></div>
    </div>
    <div class="details-actions">
      ${buttonHtml}
    </div>
  `;

  // Inject & show
  document.getElementById("detailsBody").innerHTML = html;
  const pane = document.getElementById("detailsPane");
  const content = document.getElementById("pageContent");
  pane.setAttribute("aria-hidden", "false");
  pane.classList.add("open");
  content.classList.add("shrink");
}

// Close details panel
function closeDetails() {
  const pane = document.getElementById("detailsPane");
  const content = document.getElementById("pageContent");
  pane.setAttribute("aria-hidden", "true");
  pane.classList.remove("open");
  content.classList.remove("shrink");
  document.getElementById("detailsBody").innerHTML = "";
}


/* ==========================
   Claim Form Submit
========================== */
$(function () {
  $("#claimForm").submit(function (e) {
    e.preventDefault();
    const formData = $(this).serialize();

    $.post("/claims/submitClaim", formData, function (response) {
      $("#claimModal").modal("hide");
      alert(response);
      location.reload();
    }).fail(function (xhr) {
      $("#claimModal").modal("hide");
      alert("An error occurred: " + xhr.responseText);
    });
  });
});


function openClaimPopup(donationId) {
  closeDetails(); // auto close side panel
  document.getElementById("donationIdField").value = donationId;
  $("#claimModal").modal("show");
}

function showDetailsFromCard(cardEl) {
    const d = cardEl.dataset;

    const username = d.username && d.username !== "null" ? d.username : "N/A";
    const mobile = d.mobile && d.mobile !== "null" ? d.mobile : "N/A";
    const address = d.address && d.address !== "null" ? d.address : "N/A";
    const gpc = d.googlePlusCode && d.googlePlusCode !== "null" ? d.googlePlusCode : "N/A";
    const mealType = d.mealType || "N/A";
    const status = d.status || "N/A";
    const claimTime = d.claimTime || "-";

    // Build modern card-style HTML
    const html = `
        <div class="detail-card">
            <div class="detail-row"><div class="rk">Meal</div><div class="rv">${mealType}</div></div>
            <div class="detail-row"><div class="rk">Status</div><div class="rv">${status}</div></div>
            <div class="detail-row"><div class="rk">Claim Time</div><div class="rv">${claimTime}</div></div>
            <div class="detail-row"><div class="rk">Address</div><div class="rv">${address}</div></div>
            <div class="detail-row">
                <div class="rk">Google Plus Code</div>
                <div class="rv">
                    ${gpc}
                    ${gpc && gpc !== "N/A" ? `<a class="map-link" target="_blank" href="https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(gpc)}">View on Map</a>` : ""}
                </div>
            </div>
            <div class="detail-row"><div class="rk">User</div><div class="rv">${username}</div></div>
            <div class="detail-row"><div class="rk">Mobile</div><div class="rv">${mobile}</div></div>
        </div>
    `;

    // Add action button at the bottom
    const actionButton = d.status === "CLAIMED"
        ? `<button class="details-actions-button" disabled>Request Already Placed</button>`
        : `<button class="details-actions-button" onclick="event.stopPropagation();openClaimPopup('${d.donationId}')">Place Request</button>`;

    const fullHtml = html + `<div class="details-actions">${actionButton}</div>`;

    // Inject into details panel
    const detailsBody = document.getElementById("detailsBody");
    detailsBody.innerHTML = fullHtml;

    // Show panel
    const pane = document.getElementById("detailsPane");
    const content = document.getElementById("pageContent");
    pane.setAttribute("aria-hidden", "false");
    pane.classList.add("open");
    content.classList.add("shrink");
}

// Close details panel
function closeDetails() {
    const pane = document.getElementById("detailsPane");
    const content = document.getElementById("pageContent");
    pane.setAttribute("aria-hidden", "true");
    pane.classList.remove("open");
    content.classList.remove("shrink");
    document.getElementById("detailsBody").innerHTML = "";
}
