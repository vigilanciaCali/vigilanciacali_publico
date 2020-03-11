package co.edu.usbcali.vas.model.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.vas.dataaccess.dao.IVideoDAO;
import co.edu.usbcali.vas.dataaccess.dao.IVideoDocumentDAO;
import co.edu.usbcali.vas.exceptions.ZMessManager;
import co.edu.usbcali.vas.model.Video;
import co.edu.usbcali.vas.model.VideoDocument;
import co.edu.usbcali.vas.model.dto.VideoDTO;
import co.edu.usbcali.vas.utilities.Utilities;


/**
* @author Zathura Code Generator http://zathuracode.org/
* www.zathuracode.org
*
*/
@Scope("singleton")
@Service("VideoLogic")
public class VideoLogic implements IVideoLogic {
    private static final Logger log = LoggerFactory.getLogger(VideoLogic.class);

    /**
     * DAO injected by Spring that manages Video entities
     *
     */
    @Autowired
    private IVideoDAO videoDAO;

    /**
    * DAO injected by Spring that manages VideoDocument entities
    *
    */
    @Autowired
    private IVideoDocumentDAO videoDocumentDAO;

    /**
    * Logic injected by Spring that manages Users entities
    *
    */
    @Autowired
    IUsersLogic logicUsers1;

    @Transactional(readOnly = true)
    public List<Video> getVideo() throws Exception {
        log.debug("finding all Video instances");

        List<Video> list = new ArrayList<Video>();

        try {
            list = videoDAO.findAll();
        } catch (Exception e) {
            log.error("finding all Video failed", e);
            throw new ZMessManager().new GettingException(ZMessManager.ALL +
                "Video");
        } finally {
        }

        return list;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveVideo(Video entity) throws Exception {
        log.debug("saving Video instance");

        try {
   
            if (entity.getCreatedAt() == null) {
                throw new ZMessManager().new EmptyFieldException("createdAt");
            }

            if (entity.getCreatedBy() == null) {
                throw new ZMessManager().new EmptyFieldException("createdBy");
            }

            if (entity.getDescription() == null) {
                throw new ZMessManager().new EmptyFieldException("description");
            }

            if ((entity.getDescription() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getDescription(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "description");
            }

            if ((entity.getUrl() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getUrl(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException("url");
            }

            videoDAO.save(entity);

            log.debug("save Video successful");
        } catch (Exception e) {
            log.error("save Video failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteVideo(Video entity) throws Exception {
        log.debug("deleting Video instance");

        if (entity == null) {
            throw new ZMessManager().new NullEntityExcepcion("Video");
        }

        if (entity.getId() == null) {
            throw new ZMessManager().new EmptyFieldException("id");
        }

        List<VideoDocument> videoDocuments = null;

        try {
            videoDocuments = videoDocumentDAO.findByProperty("video.id",
                    entity.getId());

            if (Utilities.validationsList(videoDocuments) == true) {
                throw new ZMessManager().new DeletingException("videoDocuments");
            }

            videoDAO.delete(entity);

            log.debug("delete Video successful");
        } catch (Exception e) {
            log.error("delete Video failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateVideo(Video entity) throws Exception {
        log.debug("updating Video instance");

        try {
            if (entity == null) {
                throw new ZMessManager().new NullEntityExcepcion("Video");
            }

            if ((entity.getAnalyticData() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getAnalyticData(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "analyticData");
            }

            if (entity.getCreatedAt() == null) {
                throw new ZMessManager().new EmptyFieldException("createdAt");
            }

            if (entity.getCreatedBy() == null) {
                throw new ZMessManager().new EmptyFieldException("createdBy");
            }

            if ((entity.getCreatedBy() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getCreatedBy(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "createdBy");
            }

            if (entity.getDescription() == null) {
                throw new ZMessManager().new EmptyFieldException("description");
            }

            if ((entity.getDescription() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getDescription(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "description");
            }

            if ((entity.getFormat() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getFormat(),
                        255) == false)) {
                throw new ZMessManager().new NotValidFormatException("format");
            }

            if (entity.getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id");
            }

            if ((entity.getInfo() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getInfo(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException("info");
            }

            if ((entity.getLenght() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getLenght(),
                        255) == false)) {
                throw new ZMessManager().new NotValidFormatException("lenght");
            }

            if (entity.getMimeType() == null) {
                throw new ZMessManager().new EmptyFieldException("mimeType");
            }

            if ((entity.getMimeType() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getMimeType(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException("mimeType");
            }

            if ((entity.getSrc() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getSrc(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException("src");
            }

            if ((entity.getType() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getType(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException("type");
            }

            if ((entity.getUpdatedBy() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getUpdatedBy(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "updatedBy");
            }

            if ((entity.getUrl() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getUrl(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException("url");
            }

            if ((entity.getVideoData() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getVideoData(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "videoData");
            }

            videoDAO.update(entity);

            log.debug("update Video successful");
        } catch (Exception e) {
            log.error("update Video failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = true)
    public List<VideoDTO> getDataVideo() throws Exception {
        try {
            List<Video> video = videoDAO.findAll();

            List<VideoDTO> videoDTO = new ArrayList<VideoDTO>();

            for (Video videoTmp : video) {
                VideoDTO videoDTO2 = new VideoDTO();

                videoDTO2.setId(videoTmp.getId());
                videoDTO2.setAnalyticData((videoTmp.getAnalyticData() != null)
                    ? videoTmp.getAnalyticData() : null);
                videoDTO2.setCreatedAt(videoTmp.getCreatedAt());
                videoDTO2.setCreatedBy((videoTmp.getCreatedBy() != null)
                    ? videoTmp.getCreatedBy() : null);
                videoDTO2.setDescription((videoTmp.getDescription() != null)
                    ? videoTmp.getDescription() : null);
                videoDTO2.setFormat((videoTmp.getFormat() != null)
                    ? videoTmp.getFormat() : null);
                videoDTO2.setInfo((videoTmp.getInfo() != null)
                    ? videoTmp.getInfo() : null);
                videoDTO2.setLenght((videoTmp.getLenght() != null)
                    ? videoTmp.getLenght() : null);
                videoDTO2.setMimeType((videoTmp.getMimeType() != null)
                    ? videoTmp.getMimeType() : null);
                videoDTO2.setSrc((videoTmp.getSrc() != null)
                    ? videoTmp.getSrc() : null);
                videoDTO2.setType((videoTmp.getType() != null)
                    ? videoTmp.getType() : null);
                videoDTO2.setUpdatedAt(videoTmp.getUpdatedAt());
                videoDTO2.setUpdatedBy((videoTmp.getUpdatedBy() != null)
                    ? videoTmp.getUpdatedBy() : null);
                videoDTO2.setUrl((videoTmp.getUrl() != null)
                    ? videoTmp.getUrl() : null);
                videoDTO2.setVideoData((videoTmp.getVideoData() != null)
                    ? videoTmp.getVideoData() : null);
                videoDTO.add(videoDTO2);
            }

            return videoDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Video getVideo(Long id) throws Exception {
        log.debug("getting Video instance");

        Video entity = null;

        try {
            entity = videoDAO.findById(id);
        } catch (Exception e) {
            log.error("get Video failed", e);
            throw new ZMessManager().new FindingException("Video");
        } finally {
        }

        return entity;
    }
    
    

    @Transactional(readOnly = true)
    public List<Video> findPageVideo(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception {
        List<Video> entity = null;

        try {
            entity = videoDAO.findPage(sortColumnName, sortAscending, startRow,
                    maxResults);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Video Count");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public Long findTotalNumberVideo() throws Exception {
        Long entity = null;

        try {
            entity = videoDAO.count();
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Video Count");
        } finally {
        }

        return entity;
    }

    /**
    *
    * @param varibles
    *            este arreglo debera tener:
    *
    * [0] = String variable = (String) varibles[i]; representa como se llama la
    * variable en el pojo
    *
    * [1] = Boolean booVariable = (Boolean) varibles[i + 1]; representa si el
    * valor necesita o no ''(comillas simples)usado para campos de tipo string
    *
    * [2] = Object value = varibles[i + 2]; representa el valor que se va a
    * buscar en la BD
    *
    * [3] = String comparator = (String) varibles[i + 3]; representa que tipo
    * de busqueda voy a hacer.., ejemplo: where nombre=william o where nombre<>william,
        * en este campo iria el tipo de comparador que quiero si es = o <>
            *
            * Se itera de 4 en 4..., entonces 4 registros del arreglo representan 1
            * busqueda en un campo, si se ponen mas pues el continuara buscando en lo
            * que se le ingresen en los otros 4
            *
            *
            * @param variablesBetween
            *
            * la diferencia son estas dos posiciones
            *
            * [0] = String variable = (String) varibles[j]; la variable ne la BD que va
            * a ser buscada en un rango
            *
            * [1] = Object value = varibles[j + 1]; valor 1 para buscar en un rango
            *
            * [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en un rango
            * ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria value2
                *
                * [3] = String comparator1 = (String) varibles[j + 3]; comparador 1
                * ejemplo: a comparator1 1 and a < 5
                    *
                    * [4] = String comparator2 = (String) varibles[j + 4]; comparador 2
                    * ejemplo: a comparador1>1  and a comparador2<5  (el original: a > 1 and a <
                            * 5) *
                            * @param variablesBetweenDates(en
                            *            este caso solo para mysql)
                            *  [0] = String variable = (String) varibles[k]; el nombre de la variable que hace referencia a
                            *            una fecha
                            *
                            * [1] = Object object1 = varibles[k + 2]; fecha 1 a comparar(deben ser
                            * dates)
                            *
                            * [2] = Object object2 = varibles[k + 3]; fecha 2 a comparar(deben ser
                            * dates)
                            *
                            * esto hace un between entre las dos fechas.
                            *
                            * @return lista con los objetos que se necesiten
                            * @throws Exception
                            */
    @Transactional(readOnly = true)
    public List<Video> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception {
        List<Video> list = new ArrayList<Video>();
        String where = new String();
        String tempWhere = new String();

        if (variables != null) {
            for (int i = 0; i < variables.length; i++) {
                if ((variables[i] != null) && (variables[i + 1] != null) &&
                        (variables[i + 2] != null) &&
                        (variables[i + 3] != null)) {
                    String variable = (String) variables[i];
                    Boolean booVariable = (Boolean) variables[i + 1];
                    Object value = variables[i + 2];
                    String comparator = (String) variables[i + 3];

                    if (booVariable.booleanValue()) {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " \'" +
                            value + "\' )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " \'" + value + "\' )");
                    } else {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " " +
                            value + " )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " " + value + " )");
                    }
                }

                i = i + 3;
            }
        }

        if (variablesBetween != null) {
            for (int j = 0; j < variablesBetween.length; j++) {
                if ((variablesBetween[j] != null) &&
                        (variablesBetween[j + 1] != null) &&
                        (variablesBetween[j + 2] != null) &&
                        (variablesBetween[j + 3] != null) &&
                        (variablesBetween[j + 4] != null)) {
                    String variable = (String) variablesBetween[j];
                    Object value = variablesBetween[j + 1];
                    Object value2 = variablesBetween[j + 2];
                    String comparator1 = (String) variablesBetween[j + 3];
                    String comparator2 = (String) variablesBetween[j + 4];
                    tempWhere = (tempWhere.length() == 0)
                        ? ("(" + value + " " + comparator1 + " " + variable +
                        " and " + variable + " " + comparator2 + " " + value2 +
                        " )")
                        : (tempWhere + " AND (" + value + " " + comparator1 +
                        " " + variable + " and " + variable + " " +
                        comparator2 + " " + value2 + " )");
                }

                j = j + 4;
            }
        }

        if (variablesBetweenDates != null) {
            for (int k = 0; k < variablesBetweenDates.length; k++) {
                if ((variablesBetweenDates[k] != null) &&
                        (variablesBetweenDates[k + 1] != null) &&
                        (variablesBetweenDates[k + 2] != null)) {
                    String variable = (String) variablesBetweenDates[k];
                    Object object1 = variablesBetweenDates[k + 1];
                    Object object2 = variablesBetweenDates[k + 2];
                    String value = null;
                    String value2 = null;

                    try {
                        Date date1 = (Date) object1;
                        Date date2 = (Date) object2;
                        value = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date1);
                        value2 = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date2);
                    } catch (Exception e) {
                        list = null;
                        throw e;
                    }

                    tempWhere = (tempWhere.length() == 0)
                        ? ("(model." + variable + " between \'" + value +
                        "\' and \'" + value2 + "\')")
                        : (tempWhere + " AND (model." + variable +
                        " between \'" + value + "\' and \'" + value2 + "\')");
                }

                k = k + 2;
            }
        }

        if (tempWhere.length() == 0) {
            where = null;
        } else {
            where = "(" + tempWhere + ")";
        }

        try {
            list = videoDAO.findByCriteria(where);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
        }

        return list;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Video getVideoByTransactionId(String transactionId) throws Exception {
        log.debug("getting Video instance");

        Video entity = null;

        try {
            entity = videoDAO.getVideoByTransactionId(transactionId);
        } catch (Exception e) {
            log.error("get Video failed", e);
            throw new ZMessManager().new FindingException("Video");
        }
        return entity;
    }
    
}
