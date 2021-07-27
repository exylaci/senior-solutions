package moviespringdatajpa;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private MovieRepository repository;
    private ModelMapper modelMapper;

    public List<MovieDto> getMovies(){
        Type targetListType=new TypeToken<List<MovieDto>>(){}.getType();
        List<Movie> movies=repository.findAll();

        return modelMapper.map(movies, targetListType);
    }
}
