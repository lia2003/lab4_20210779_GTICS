package com.example.lab4_20210779.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.lab4_20210779.repository.FlorRepository;
import com.example.lab4_20210779.entity.Flor;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import com.example.lab4_20210779.entity.ItemCarrito;


@Controller
@RequestMapping("/flor")
public class FlorController {
    @Autowired
    private FlorRepository florRepository;

    // Métodos para manejar las rutas
    @GetMapping("/catalogo")
    public String mostrarCatalogo(@RequestParam(required = false) String color,
                                  @RequestParam(required = false) String tipo,
                                  @RequestParam(required = false) String ocasion,
                                  Model model) {
        List<Flor> flores;
        if (color != null && tipo != null && ocasion != null) {
            flores = florRepository.findByColorAndTipoAndOcasion(color, tipo, ocasion);
        } else {
            flores = florRepository.findAll();
        }
        model.addAttribute("flores", flores);
        model.addAttribute("totalFlores", flores.size());
        return "catalogo";
    }


    @Controller
    @RequestMapping("/carrito")
    @SessionAttributes("carrito")
    public class CarritoController {

        @ModelAttribute("carrito")
        public List<ItemCarrito> crearCarrito() {
            return new ArrayList<>();
        }

        @PostMapping("/agregar")
        public String agregarAlCarrito(@RequestParam("florId") Integer florId,
                                       @RequestParam("cantidad") Integer cantidad,
                                       @ModelAttribute("carrito") List<ItemCarrito> carrito,
                                       Model model) {
            // Obtener la flor por ID
            Flor flor = florRepository.findById(florId).orElseThrow(() -> new IllegalArgumentException("Flor no encontrada"));

            // Buscar si la flor ya está en el carrito
            for (ItemCarrito item : carrito) {
                if (item.getFlor().getId().equals(florId)) {
                    // Si ya está, aumentar la cantidad
                    item.setCantidad(item.getCantidad() + cantidad);
                    model.addAttribute("carrito", carrito);
                    return "redirect:/catalogo";
                }
            }

            // Si no está, agregarla al carrito
            carrito.add(new ItemCarrito(flor, cantidad));
            model.addAttribute("carrito", carrito);
            return "redirect:/catalogo";
        }

        @PostMapping("/quitar")
        public String quitarDelCarrito(@RequestParam("florId") Integer florId,
                                       @ModelAttribute("carrito") List<ItemCarrito> carrito,
                                       Model model) {
            // Eliminar la flor del carrito
            carrito.removeIf(item -> item.getFlor().getId().equals(florId));
            model.addAttribute("carrito", carrito);
            return "redirect:/carrito";
        }

        @PostMapping("/comprar")
        public String comprarDirectamente(@RequestParam("florId") Integer florId,
                                          @ModelAttribute("carrito") List<ItemCarrito> carrito,
                                          Model model) {
            // Eliminar el item del carrito tras comprarlo
            carrito.removeIf(item -> item.getFlor().getId().equals(florId));

            // Simular la compra (Podrías guardar la información de compra en la BD)
            model.addAttribute("carrito", carrito);
            return "redirect:/compra-exitosa"; // Redirigir a una página de confirmación
        }
    }
}
