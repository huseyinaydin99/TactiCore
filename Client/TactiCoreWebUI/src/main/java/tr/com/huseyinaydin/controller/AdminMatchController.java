package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;
import tr.com.huseyinaydin.dto.matchevent.CreateMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.model.MatchEventsViewModel;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/matches")
public class AdminMatchController extends AbstractBaseController {

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isBlank()) {
                    setValue(null);
                } else {
                    setValue(LocalDateTime.parse(text.trim(), DT_FMT));
                }
            }
            @Override
            public String getAsText() {
                Object value = getValue();
                return value instanceof LocalDateTime ? DT_FMT.format((LocalDateTime) value) : "";
            }
        });
    }

    @GetMapping
    public String list(Model model) {
        try {
            addCommonAttributes(model);
            List<ResultMatchDto> matches = serviceFactory.getMatchService().getAll();
            if (matches == null) matches = Collections.emptyList();
            model.addAttribute("matches", matches);
            model.addAttribute("pageTitle", "Mac Yonetimi");
            return "admin/match/list";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/match/list");
        }
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        try {
            addCommonAttributes(model);
            model.addAttribute("matchForm", new UpdateMatchDto());
            model.addAttribute("isEdit", false);
            model.addAttribute("teams", getTeams());
            model.addAttribute("pageTitle", "Yeni Mac Ekle");
            return "admin/match/form";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/match/form");
        }
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("matchForm") UpdateMatchDto dto, RedirectAttributes ra) {
        try {
            serviceFactory.getMatchService().create(
                    new CreateMatchDto(dto.getHomeTeamId(), dto.getAwayTeamId(),
                            dto.getHomeScore(), dto.getAwayScore(),
                            dto.getMatchDate(), dto.getStadium(),
                            dto.getWeek(), dto.getStatus(),
                            dto.getMinute(), dto.isFeatured()));
            ra.addFlashAttribute("successMessage", "Mac basariyla eklendi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Mac eklenirken hata olustu: " + ex.getMessage());
        }
        return "redirect:/admin/matches";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        try {
            addCommonAttributes(model);
            GetMatchByIdDto existing = serviceFactory.getMatchService().getById(id);
            UpdateMatchDto form = new UpdateMatchDto();
            form.setId(existing.getId());
            form.setHomeTeamId(existing.getHomeTeamId());
            form.setAwayTeamId(existing.getAwayTeamId());
            form.setHomeScore(existing.getHomeScore());
            form.setAwayScore(existing.getAwayScore());
            form.setMatchDate(existing.getMatchDate());
            form.setStadium(existing.getStadium());
            form.setWeek(existing.getWeek());
            form.setStatus(existing.getStatus());
            form.setMinute(existing.getMinute());
            form.setFeatured(existing.isFeatured());
            model.addAttribute("matchForm", form);
            model.addAttribute("isEdit", true);
            model.addAttribute("matchId", id);
            model.addAttribute("teams", getTeams());
            model.addAttribute("pageTitle", "Maci Duzenle");
            return "admin/match/form";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/match/form");
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable String id,
                       @ModelAttribute("matchForm") UpdateMatchDto dto,
                       RedirectAttributes ra) {
        try {
            dto.setId(id);
            serviceFactory.getMatchService().update(dto);
            ra.addFlashAttribute("successMessage", "Mac basariyla guncellendi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Guncelleme sirasinda hata olustu: " + ex.getMessage());
        }
        return "redirect:/admin/matches";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            serviceFactory.getMatchService().delete(id);
            ra.addFlashAttribute("successMessage", "Mac basariyla silindi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Silme islemi basarisiz: " + ex.getMessage());
        }
        return "redirect:/admin/matches";
    }

    @GetMapping("/{id}/events")
    public String events(@PathVariable String id, Model model) {
        try {
            addCommonAttributes(model);
            GetMatchByIdDto match = serviceFactory.getMatchService().getById(id);
            List<ResultMatchEventDto> events = serviceFactory.getMatchEventService().getByMatchId(id);
            List<ResultPlayerDto> players = serviceFactory.getPlayerService().getAll();
            if (events == null) events = Collections.emptyList();
            if (players == null) players = Collections.emptyList();
            model.addAttribute("matchVm", new MatchEventsViewModel(match, events, players));
            model.addAttribute("eventForm", new CreateMatchEventDto());
            model.addAttribute("pageTitle", "Mac Olaylari");
            return "admin/match/events";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/match/events");
        }
    }

    @PostMapping("/{id}/events/add")
    public String addEvent(@PathVariable String id,
                           @ModelAttribute("eventForm") CreateMatchEventDto dto,
                           RedirectAttributes ra) {
        try {
            dto.setMatchId(id);
            serviceFactory.getMatchEventService().create(dto);
            ra.addFlashAttribute("successMessage", "Olay basariyla eklendi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Olay eklenirken hata olustu: " + ex.getMessage());
        }
        return "redirect:/admin/matches/" + id + "/events";
    }

    @PostMapping("/{id}/events/delete/{eventId}")
    public String deleteEvent(@PathVariable String id,
                              @PathVariable String eventId,
                              RedirectAttributes ra) {
        try {
            serviceFactory.getMatchEventService().delete(eventId);
            ra.addFlashAttribute("successMessage", "Olay basariyla silindi.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Silme islemi basarisiz: " + ex.getMessage());
        }
        return "redirect:/admin/matches/" + id + "/events";
    }

    private List<ResultTeamDto> getTeams() {
        List<ResultTeamDto> teams = serviceFactory.getTeamService().getAll();
        return teams != null ? teams : Collections.emptyList();
    }
}
