package src.automacao;
import java.io.Serializable;
import src.controller.*;//DomusControl
import src.model.*;


public interface Condicao extends Serializable {
    boolean verificar(DomusControl dc);
    Condicao clone();


    //metodo que deteta chuva, para ser usado na automacao de fechar as cortinas quando estiver a chover
    static Condicao detetarChuva(int idCasa, int idDivisao, int idSensor){
        return new Condicao(){
            public boolean verificar(DomusControl dc){
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return false;
                Divisao divisao = dc.encontrarDivisaoPorId(casa, idDivisao); 
                if (divisao == null) return false;
                Dispositivo dispositivo = dc.encontrarDispositivoPorId(divisao, idSensor); 
                if (dispositivo instanceof SensorAgua sensor) return sensor.isEmChuva();
                return false;
            }
            public Condicao clone(){
                return detetarChuva(idCasa, idDivisao, idSensor);
            }
        };
    }
    
}
