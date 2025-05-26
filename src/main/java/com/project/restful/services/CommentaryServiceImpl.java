package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.dtos.commentary.CommentaryResponseDto;
import com.project.restful.dtos.commentary.CommnetaryDto;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.models.Transaction;
import com.project.restful.models.Users;
import com.project.restful.models.Valorations;
import com.project.restful.services.interfacesLogic.CommentaryService;
import com.project.restful.utils.Tools;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentaryServiceImpl implements CommentaryService {


    private Tools tools;
    private DaoCrud<Valorations> valorationsDaoCrud;

    private DaoCrud<Transaction> transactionDaoCrud;

    /*
     Se crea un comentario cuando tenemos un transación activa
     */

    @Override
    public void createCommentary(CommnetaryDto commnetaryDto) {
        Users userUnknowm = tools.findPersonByToken(commnetaryDto.tokenDto().token());
        Transaction transaction = transactionDaoCrud.get(commnetaryDto.idTransaction()).orElseThrow();
        findCommentUserInTransaction(transaction,userUnknowm.getId());
        Valorations valorations = setDataValoration(commnetaryDto.message(),(byte) commnetaryDto.valoration(),userUnknowm);
        Valorations valorationsCreate = valorationsDaoCrud.create(valorations).orElseThrow();

        Optional.of(findIsIfSeller(transaction,userUnknowm))
                .map(flat-> transactionSetOwnerValoration(transaction,valorationsCreate,flat))
                .map(transactionSet->  transactionDaoCrud.update(transactionSet));
    }

    /*
        Busca un comentario para comprobar su estado y si esta cerrado lanza una exepción de que el comentario de
        la publicación esta cerrado
     */
    public void findCommentUserInTransaction(Transaction transaction,Long idUserKnown){
        if(transaction.getValorationsSeller()!=null && Objects.equals(transaction.getValorationsSeller().getUsers().getId(), idUserKnown)){
            throw new BadRequestExeption("Ya comentaste en esta publicación");
        }

        if(transaction.getValorationsBidder()!=null && Objects.equals(transaction.getValorationsBidder().getUsers().getId(),idUserKnown)){
            throw new BadRequestExeption("Ya comentaste en esta publicación");
        }
    }

    private Valorations setDataValoration(String message,byte valoration,Users users){
        Valorations valorations = new Valorations();
        valorations.setId(null);
        valorations.setComentary(message);
        valorations.setValoration(valoration);
        valorations.setUsers(users);
        return valorations;
    }

    /*
        Setea quien es dueño del comentarío en la transaccion, me devuelve una transacción
     */
    private Transaction transactionSetOwnerValoration(Transaction transaction,Valorations valorations,Boolean isSeller){
        if (!isSeller){
            transaction.setValorationsBidder(valorations);
            return transaction;
        }
        transaction.setValorationsSeller(valorations);
        return transaction;
    }

    /*
        Comprueba de que una transacción permita comentar de acuerdo a su id
        comparando los 2 estados de la transacción se puede determinar si esta vendido
     */
    private boolean findIsIfSeller(Transaction transaction,Users userUnknown){
        return Objects.equals(transaction.getUserSeller().getId(), userUnknown.getId());
    }

    /*
       Metodo para convertir un CommentaryResponseDto de acuerdo a una Valorations
    */
    @Override
    public CommentaryResponseDto convertToDto(Valorations valorations) {
        return new CommentaryResponseDto(valorations.getId(),valorations.getComentary(),valorations.getValoration(),valorations.getUsers().getName());
    }



}


