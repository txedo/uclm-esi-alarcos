package es.uclm.inf_cr.alarcos.desglosa_web.model.util;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public class TransientAttributesListener implements PostLoadEventListener {
    private static final long serialVersionUID = -4143306987360141117L;

    public void onPostLoad(PostLoadEvent event) {
        if (event.getEntity() instanceof Factory) {
            ((Factory) event.getEntity()).updateTransientFields();
            ((Factory) event.getEntity()).updateMeasures();
        }
    }
}
