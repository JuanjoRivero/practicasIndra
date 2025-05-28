document.addEventListener('DOMContentLoaded', function() {
    // Carrusel (solo si existe)
    const slides = document.querySelectorAll('.slide');
    const indicadores = document.querySelectorAll('.indicador');
    let actual = 0;

    function mostrarSlide(n) {
        slides.forEach((slide, i) => {
            slide.classList.toggle('activo', i === n);
            if (indicadores[i]) indicadores[i].classList.toggle('activo', i === n);
        });
    }

    if (slides.length && indicadores.length) {
        const btnAnterior = document.getElementById('anterior');
        const btnSiguiente = document.getElementById('siguiente');
        if (btnAnterior && btnSiguiente) {
            btnAnterior.addEventListener('click', function() {
                actual = (actual - 1 + slides.length) % slides.length;
                mostrarSlide(actual);
            });

            btnSiguiente.addEventListener('click', function() {
                actual = (actual + 1) % slides.length;
                mostrarSlide(actual);
            });
        }

        indicadores.forEach((indicador, i) => {
            indicador.addEventListener('click', function() {
                actual = i;
                mostrarSlide(actual);
            });
        });

        mostrarSlide(actual);
    }

    // Filtro de eventos con botón (solo si existe)
    const filtro = document.getElementById('filtroTipo');
    const btnFiltrar = document.getElementById('btnFiltrar');
    const eventosLista = document.querySelector('.eventos-lista');
    const eventos = eventosLista ? Array.from(eventosLista.querySelectorAll('.evento')) : [];

    if (filtro && btnFiltrar && eventos.length) {
        btnFiltrar.addEventListener('click', function() {
            const tipoSeleccionado = filtro.value;

            // Filtrar eventos por tipo usando la clase del span.tipo
            eventos.forEach(ev => {
                const spanTipo = ev.querySelector('.tipo');
                let tipoEvento = '';
                if (spanTipo) {
                    // Busca la clase que sea 'taller', 'conferencia' o 'actividad'
                    if (spanTipo.classList.contains('taller')) tipoEvento = 'taller';
                    else if (spanTipo.classList.contains('conferencia')) tipoEvento = 'conferencia';
                    else if (spanTipo.classList.contains('actividad')) tipoEvento = 'actividad';
                }
                if (tipoSeleccionado === 'todos' || tipoEvento === tipoSeleccionado) {
                    ev.style.display = '';
                } else {
                    ev.style.display = 'none';
                }
            });

            // Ordenar los eventos filtrados por fecha
            const eventosFiltrados = eventos.filter(ev => ev.style.display !== 'none');
            eventosFiltrados.sort((a, b) => {
                const fechaA = a.getAttribute('data-fecha') || '';
                const fechaB = b.getAttribute('data-fecha') || '';
                return fechaA.localeCompare(fechaB);
            });
            // Reinsertar en el DOM en el nuevo orden
            eventosFiltrados.forEach(ev => eventosLista.appendChild(ev));
        });
    }
});