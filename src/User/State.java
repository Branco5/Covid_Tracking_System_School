package User;

/**
 * Defines possible states for users
 */
public enum State {
    CONTINUOUS, ISOLATION, INFECTED;

    @Override
    public String toString() {
        switch (this){
            case INFECTED : return "Infetado";

            case ISOLATION : return "Isolado";

            case CONTINUOUS : return "Contínuo";

            default : return "Desconhecido";
        }
    }
}
