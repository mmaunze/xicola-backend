package mz.co.mefemasys.xicola.backend.models.dto;

import mz.co.mefemasys.xicola.backend.models.Comunicado;
import java.time.Instant;
import lombok.Data;

@Data
public class ComunicadoDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private Instant dataPublicacao;
    private Long responsavel;
    private String destinatario;
    private String estado;

    public ComunicadoDTO(Comunicado comunicado) {
        this.id = comunicado.getId();
        this.titulo = comunicado.getTitulo();
        this.conteudo = comunicado.getConteudo();
        this.dataPublicacao = comunicado.getDataPublicacao();
        this.responsavel = comunicado.getResponsavel().getId();
        this.destinatario = comunicado.getDestinatario().getDescricao();
        this.estado = comunicado.getEstado().getDescricao();
    }
}
