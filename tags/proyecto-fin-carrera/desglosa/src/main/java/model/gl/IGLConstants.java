package model.gl;

public interface IGLConstants {
    final int INFINITE = 99999999;
    final float INIT_DIM = 10.f;
    // Constantes que definen la granularidad de rotacion de la camara en el eje
    // X e Y
    final float X_ROTATION = 0.5f;
    final float Y_ROTATION = 0.5f;
    // Constante que define la granularidad del zoom
    final float ZOOM = 0.15f;
    final float ZOOM_MAX = 15.0f;
    // Constante que define la granularidad de desplazamiento de la camara
    final float DESPL = 0.05f;
}
