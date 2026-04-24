package src.automacao;
import java.io.Serializable;
import src.controller.*;//DomusControl


public interface Condicao extends Serializable {
    boolean verificar(DomusControl dc);
    Condicao clone();
    
}
