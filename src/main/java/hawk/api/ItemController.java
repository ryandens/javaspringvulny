package hawk.api;

import hawk.form.Search;
import hawk.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/V1/items")
public class ItemController {

    private final SearchService searchService;

    @Autowired
    public ItemController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/{text}")
    public ResponseEntity search(@PathVariable("text") String text) {
        Search search = new Search(text);
        return ResponseEntity.ok(searchService.search(search));
    }
}