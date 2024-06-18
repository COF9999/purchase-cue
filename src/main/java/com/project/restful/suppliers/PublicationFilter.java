package com.project.restful.suppliers;

import com.project.restful.dtos.publish.MultiConsultDto;
import com.project.restful.models.Publications;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationFilter {

    /**
     * Apartir de toda la lista de publicaciones buscamos mediante los campos de Dto
     * Se puede filtrar por precio,categorio,condición
     * @param listPublications
     * @param campus
     * @return List<Publications>
     */


    public static List<Publications> find(List<Publications> listPublications,MultiConsultDto campus){

        List<String> searchCategory = campus.category().size() > 0 ? campus.category() : null;
        Double[] range = campus.price().length>0 ? campus.price() : null;
        Double[] condition = campus.condition().length>0  ? campus.condition() : null;

        return listPublications.stream()
                .filter(Publications::getActive)
                .filter(p-> searchCategory!=null ?filterCategory(p.getProduct().getCategory(),searchCategory):true)
                .filter(p-> (range != null)?filterRange(p.getProduct().getPrice(),range):true)
                .filter(p-> (condition!=null)?filterRange(Double.valueOf(p.getProduct().getCondition()),condition):true)
                .collect(Collectors.toList());

    }


    private static boolean filterCategory(String p, List<String> search) {
        for (String s:search){
            if(p.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    private static boolean filterRange(Double start,Double[] range){
        return start >= range[0] && start <= range[1];
    }
}
