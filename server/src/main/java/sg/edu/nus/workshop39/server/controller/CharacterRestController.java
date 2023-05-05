package sg.edu.nus.workshop39.server.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.workshop39.server.model.Comment;
import sg.edu.nus.workshop39.server.model.MarvelCharacter;
import sg.edu.nus.workshop39.server.services.CharacterService;

@RestController
@RequestMapping(path="/api/characters", consumes=MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterRestController {
    
    @Autowired
    private CharacterService charsvc;

    @GetMapping
    public ResponseEntity<String> getCharacters(
        @RequestParam(required=true) String charName,
        @RequestParam(required=true) Integer limit,
        @RequestParam(required=true) Integer offset
        
    ){
        System.out.println("char >" + charName);
        JsonArray result = null;
        Optional<List<MarvelCharacter>> arr = this.charsvc.getCharacters(charName, limit, offset);
        List<MarvelCharacter> aa = arr.get();
        JsonArrayBuilder arrBld = Json.createArrayBuilder();
        for(MarvelCharacter c: aa)
            arrBld.add(c.toJSON());
        result = arrBld.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path="/{charId}")
    public ResponseEntity<String> getCharacterDetails(
        @PathVariable(required=true) String charId
    ) throws IOException{
        System.out.println(charId);
        MarvelCharacter c = this.charsvc.getCharacterDetails(charId);
        JsonObjectBuilder bld = Json.createObjectBuilder()
            .add("details", c.toJSON());
        JsonObject result = bld.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @PostMapping(path="/{charId}")
    public ResponseEntity<String> saveCharacterComment(
        @RequestBody Comment comment, 
        @PathVariable(required=true) String charId
    ){
        System.out.println("save Comment!");
        System.out.println("charId? " + charId);
        System.out.println("comment? " + comment.getCharId());
        comment.setComment(comment.getComment());
        comment.setCharId(charId);
        Comment r= this.charsvc.insertComment(comment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(r.toString());
    }

    @GetMapping(path="/comments/{charId}")
    public ResponseEntity<String> getCharComments(@PathVariable(required=true) String charId){
        List<Comment> aa=  this.charsvc.getAllComments(charId);
        JsonArrayBuilder bld = Json.createArrayBuilder();
        for(Comment a : aa)
            bld.add(a.toJSON());
        JsonArray result =bld.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}
