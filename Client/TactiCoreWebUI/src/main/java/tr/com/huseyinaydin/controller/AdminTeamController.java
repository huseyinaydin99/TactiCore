package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.huseyinaydin.dto.team.CreateTeamDto;
import tr.com.huseyinaydin.dto.team.GetTeamByIdDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.dto.team.UpdateTeamDto;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/teams")
public class AdminTeamController extends AbstractBaseController {

    @GetMapping
    public String list(Model model) {
        try {
            addCommonAttributes(model);
            List<ResultTeamDto> teams = serviceFactory.getTeamService().getAll();
            if (teams == null) teams = Collections.emptyList();
            model.addAttribute("teams", teams);
            model.addAttribute("pageTitle", "Takım Yönetimi");
            return "admin/team/list";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/team/list");
        }
    }

    @GetMapping("/new")
    public String newRedirect() {
        return "redirect:/admin/teams/add";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        addCommonAttributes(model);
        model.addAttribute("teamForm", new UpdateTeamDto());
        model.addAttribute("isEdit", false);
        model.addAttribute("pageTitle", "Yeni Takım Ekle");
        return "admin/team/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("teamForm") UpdateTeamDto dto, RedirectAttributes ra) {
        try {
            serviceFactory.getTeamService().create(
                    new CreateTeamDto(dto.getName(), dto.getShortName(),
                            dto.getLogoUrl(), dto.getCity(), dto.getStadium(), dto.isActive()));
            ra.addFlashAttribute("successMessage", "Takım başarıyla eklendi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Takım eklenirken hata oluştu: " + ex.getMessage());
        }
        return "redirect:/admin/teams";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        try {
            addCommonAttributes(model);
            GetTeamByIdDto existing = serviceFactory.getTeamService().getById(id);
            model.addAttribute("teamForm", new UpdateTeamDto(
                    existing.getId(), existing.getName(), existing.getShortName(),
                    existing.getLogoUrl(), existing.getCity(), existing.getStadium(), existing.isActive()));
            model.addAttribute("isEdit", true);
            model.addAttribute("teamId", id);
            model.addAttribute("pageTitle", "Takımı Düzenle");
            return "admin/team/form";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/team/form");
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable String id,
                       @ModelAttribute("teamForm") UpdateTeamDto dto,
                       RedirectAttributes ra) {
        try {
            dto.setId(id);
            serviceFactory.getTeamService().update(dto);
            ra.addFlashAttribute("successMessage", "Takım başarıyla güncellendi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Güncelleme sırasında hata oluştu: " + ex.getMessage());
        }
        return "redirect:/admin/teams";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            serviceFactory.getTeamService().delete(id);
            ra.addFlashAttribute("successMessage", "Takım başarıyla silindi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Silme işlemi başarısız: " + ex.getMessage());
        }
        return "redirect:/admin/teams";
    }
}
