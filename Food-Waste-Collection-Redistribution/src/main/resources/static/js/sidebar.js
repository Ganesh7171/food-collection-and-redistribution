

    // Loader
    window.addEventListener('load', function () {
      const loader = document.getElementById('loader-wrapper');
      loader.style.opacity = '0';
      setTimeout(() => loader.style.display = 'none', 500);
    });

    // Show loader on navigation
    document.querySelectorAll('a').forEach(link => {
      link.addEventListener('click', function () {
        const href = this.getAttribute('href');
        if (href && !href.startsWith('#') && !href.startsWith('javascript')) {
          const loader = document.getElementById('loader-wrapper');
          loader.style.display = 'flex';
          loader.style.opacity = '1';
        }
      });
    });

    // Sidebar toggle
    function toggleNav() {
      document.getElementById('sideNav').classList.toggle('open');
      document.getElementById('pageContent').classList.toggle('shifted');
    }
