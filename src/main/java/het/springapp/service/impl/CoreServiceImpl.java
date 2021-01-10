package het.springapp.service.impl;

import org.springframework.stereotype.Service;

import het.springapp.model.Note;
import het.springapp.service.CoreService;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
        
@Service
public class CoreServiceImpl implements CoreService{

	public Note saveNote() throws Exception {
		// TODO Auto-generated method stub
                String [] argarray = {"", "", ""};
                List<String> argList = Arrays.asList(argarray);
                List<String> results = new ArrayList<>();
                for (String str : argList) {
                    if (argList.indexOf(str)!= argList.lastIndexOf(str)){
                        if (!results.contains(str)){
                            results.add(str);
                        }
                    }
                }
		return null;
	}
 
}
