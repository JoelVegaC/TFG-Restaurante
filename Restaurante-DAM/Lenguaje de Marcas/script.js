// ==============================
// ANIMACIÓN SCROLL
// ==============================
const elementosAnimados = document.querySelectorAll(".scroll-animado");

function animarScroll() {
    elementosAnimados.forEach(el => {
        const posicion = el.getBoundingClientRect().top;
        if (posicion < window.innerHeight - 100) {
            el.classList.add("scroll-visible");
        }
    });
}

window.addEventListener("scroll", animarScroll);
animarScroll();

// ==============================
// TRANSICIÓN ENTRE PÁGINAS
// ==============================
document.addEventListener("DOMContentLoaded", () => {
    const body = document.body;
    if (body.classList.contains("pagina")) {
        requestAnimationFrame(() => {
            body.classList.add("pagina-visible");
        });
    }

    const enlacesPagina = document.querySelectorAll(".enlace-pagina");
    enlacesPagina.forEach(enlace => {
        enlace.addEventListener("click", (e) => {
            const href = enlace.getAttribute("href");
            if (!href || href.startsWith("#")) return;

            e.preventDefault();
            body.classList.add("pagina-saliendo");
            setTimeout(() => {
                window.location.href = href;
            }, 400);
        });
    });

    const enlacesAncla = document.querySelectorAll(".enlace-ancla");
    enlacesAncla.forEach(enlace => {
        enlace.addEventListener("click", (e) => {
            const destino = enlace.getAttribute("href");
            if (destino && destino.startsWith("#")) {
                e.preventDefault();
                const objetivo = document.querySelector(destino);
                if (objetivo) {
                    objetivo.scrollIntoView({ behavior: "smooth" });
                }
            }
        });
    });
});

// ==============================
// RESERVAS
// ==============================
const formulario = document.getElementById("formulario-reserva");
const mensajeReserva = document.getElementById("mensaje-reserva");

const modalReserva = document.getElementById("modal-reserva");
const textoModal = document.getElementById("texto-modal");
const botonCerrarModal = document.getElementById("boton-cerrar-modal");

function guardarReserva(reserva) {
    const reservas = JSON.parse(localStorage.getItem("reservas")) || [];
    reservas.push(reserva);
    localStorage.setItem("reservas", JSON.stringify(reservas));
}

function mostrarModalReserva(texto) {
    if (!modalReserva || !textoModal) return;
    textoModal.textContent = texto;
    modalReserva.classList.remove("oculto");
}

if (botonCerrarModal && modalReserva) {
    botonCerrarModal.addEventListener("click", () => {
        modalReserva.classList.add("oculto");
    });
}

if (formulario) {
    formulario.addEventListener("submit", (e) => {
        e.preventDefault();

        const nombre = document.getElementById("nombre").value.trim();
        const personas = document.getElementById("personas").value;
        const fecha = document.getElementById("fecha").value;
        const mesa = document.getElementById("mesa-tematica").value;

        if (!nombre || !personas || !fecha) {
            if (mensajeReserva) {
                mensajeReserva.textContent = "Por favor, completa todos los campos.";
            }
            return;
        }

        const reserva = {
            nombre,
            personas,
            fecha,
            mesa,
            timestamp: new Date().toISOString()
        };

        guardarReserva(reserva);

        mostrarModalReserva(
            `Reserva confirmada para ${nombre} el día ${fecha} en la mesa temática ${mesa}.`
        );

        if (mensajeReserva) {
            mensajeReserva.textContent = "Tu reserva ha sido registrada correctamente.";
        }

        formulario.reset();
    });
}

// ==============================
// MODAL DE PLATOS / BEBIDAS
// ==============================
const modalPlato = document.getElementById("modal-plato");
const modalPlatoImagen = document.getElementById("modal-plato-imagen");
const modalPlatoTitulo = document.getElementById("modal-plato-titulo");
const modalPlatoDescripcion = document.getElementById("modal-plato-descripcion");
const cerrarModalPlato = document.getElementById("cerrar-modal-plato");

function abrirModalPlato(imgSrc, titulo, descripcion) {
    if (!modalPlato) return;
    modalPlatoImagen.src = imgSrc;
    modalPlatoImagen.alt = titulo;
    modalPlatoTitulo.textContent = titulo;
    modalPlatoDescripcion.textContent = descripcion;
    modalPlato.classList.remove("oculto");
}

if (cerrarModalPlato && modalPlato) {
    cerrarModalPlato.addEventListener("click", () => {
        modalPlato.classList.add("oculto");
    });

    modalPlato.addEventListener("click", (e) => {
        if (e.target === modalPlato) {
            modalPlato.classList.add("oculto");
        }
    });
}

const tarjetasPlato = document.querySelectorAll(".plato-card, .bebida-card");
tarjetasPlato.forEach(card => {
    card.addEventListener("click", () => {
        const img = card.querySelector("img");
        const info = card.querySelector(".info, .bebida-info");
        if (!img || !info) return;
        const titulo = info.querySelector("h3")?.textContent || "";
        const descripcion = info.querySelector("p")?.textContent || "";
        abrirModalPlato(img.src, titulo, descripcion);
    });
});

// ==============================
// FILTRO DE BÚSQUEDA EN CARTA
// ==============================
const buscador = document.getElementById("buscador-platos");

if (buscador) {
    buscador.addEventListener("input", () => {
        const termino = buscador.value.toLowerCase();
        const tarjetas = document.querySelectorAll(".plato-card, .bebida-card");

        tarjetas.forEach(card => {
            const texto = card.textContent.toLowerCase();
            if (texto.includes(termino)) {
                card.style.display = "";
            } else {
                card.style.display = "none";
            }
        });
    });
}

// ==============================
// UTILIDAD: OBTENER RESERVAS (si lo necesitas)
// ==============================
function obtenerReservas() {
    return JSON.parse(localStorage.getItem("reservas")) || [];
}
