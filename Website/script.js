// Add smooth scrolling to navigation links
const links = document.querySelectorAll('nav a');

links.forEach(link => {
  link.addEventListener('click', function(e) {
    e.preventDefault();
    const target = document.querySelector(this.getAttribute('href'));
    const yCoordinate = target.offsetTop;

    window.scrollTo({
      top: yCoordinate,
      behavior: 'smooth'
    });
  });
});