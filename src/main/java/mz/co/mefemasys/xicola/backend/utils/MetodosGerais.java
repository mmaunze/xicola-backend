package mz.co.mefemasys.xicola.backend.utils;

import jakarta.validation.constraints.NotNull;

import javax.swing.*;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;

import static java.util.Calendar.*;

public interface MetodosGerais {


    Random random = new Random();

    default LocalDate converterStringParaData(String dataString) throws DateTimeParseException {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataString, formato);
    }

    default String converterDataParaString(@NotNull LocalDate data) {
        String dia = String.format("%02d", data.getDayOfMonth());
        String mes = data.getMonth().getDisplayName(TextStyle.SHORT_STANDALONE, new Locale("pt", "PT"));
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
        String ano = String.valueOf(data.getYear());
        return String.format("%s de %s de %s", dia, mes, ano);
    }

    default int calcularIdade(String dataNascimento) {
        try {
            var sdf = new SimpleDateFormat("dd/MM/yyyy");
            var dataNasc = sdf.parse(dataNascimento);
            Calendar dataNascimentoCal = new GregorianCalendar();
            dataNascimentoCal.setTime(dataNasc);

            var hoje = getInstance();
            var idadee = hoje.get(YEAR) - dataNascimentoCal.get(YEAR);
            if (hoje.get(DAY_OF_YEAR) < dataNascimentoCal.get(DAY_OF_YEAR)) {
                idadee--;
            }
            return idadee;
        } catch (ParseException e) {
            return 0;
        }
    }

    default String calcularFaixaEtaria(int idade) {
        if (idade < 18) {
            return "<18";
        } else if (idade <= 25) {
            return "18-25";
        } else if (idade <= 31) {
            return "26-31";
        } else if (idade <= 37) {
            return "32-37";
        } else if (idade <= 41) {
            return "38-41";
        } else if (idade <= 48) {
            return "42-48";
        } else if (idade <= 54) {
            return "49-54";
        } else if (idade <= 59) {
            return "55-59";
        } else if (idade <= 65) {
            return "60-65";
        } else {
            return ">65";
        }
    }

    default String obterMesPorExtenso(int numeroMes) {
        if (numeroMes < 1 || numeroMes > 12) {
            throw new IllegalArgumentException("Número de mês inválido. Deve estar entre 1 e 12.");
        }
        var meses = new DateFormatSymbols(new Locale("pt", "BR")).getMonths();
        return meses[numeroMes - 1];
    }

    default void habilitarCampo(JTextField campo) {
        campo.setEnabled(true);
        campo.setEditable(true);
        campo.setFocusable(true);
        campo.requestFocus();
    }

    default int obterNumeroMes(String nomeMes) {
        var symbols = new DateFormatSymbols(new Locale("pt", "BR"));
        var meses = symbols.getMonths();
        for (var i = 0; i < meses.length; i++) {
            if (meses[i].equalsIgnoreCase(nomeMes)) {
                return i + 1;
            }
        }
        return 0;
    }

    default String generateUsername(String nomeCompleto) {

        String[] partesNome = nomeCompleto.split(" ");

        if (partesNome.length == 1) {
            return partesNome[0].toLowerCase();
        }

        String primeiroNome = partesNome[0].toLowerCase();
        String ultimoNome = partesNome[partesNome.length - 1].toLowerCase();


        String[] opcoesUsername = new String[]{
                primeiroNome + "." + ultimoNome,
                ultimoNome + "." + primeiroNome,
                primeiroNome + "." + primeiroNome,
                nomeCompleto.replace(" ", ".").toLowerCase()
        };


        return opcoesUsername[0];
    }

    default List<String> gerarUsernames(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new IllegalArgumentException("Nome completo não pode estar vazio");
        }

        var partesNome = nomeCompleto.trim().toLowerCase().split("\\s+");
        var numPalavras = partesNome.length;
        List<String> usernames = new ArrayList<>();

        // Adiciona combinações iniciais
        var sb = new StringBuilder();
        for (var parte : partesNome) {
            sb.append(parte.charAt(0));
        }
        usernames.add(sb + partesNome[numPalavras - 1]);
        usernames.add(sb + "." + partesNome[numPalavras - 1]);

        // Método auxiliar para gerar combinações
        generateCombinations(partesNome, numPalavras, usernames, 2);
        generateCombinations(partesNome, numPalavras, usernames, 3);
        generateCombinations(partesNome, numPalavras, usernames, 4);
        generateCombinations(partesNome, numPalavras, usernames, 5);

        return usernames;
    }

    private void generateCombinations(String[] partesNome, int numPalavras, List<String> usernames, int combinationSize) {
        if (numPalavras >= combinationSize) {
            int[] indices = new int[combinationSize];
            combine(partesNome, usernames, indices, 0, numPalavras - 1, 0, combinationSize);
        }
    }

    private void combine(String[] partesNome, List<String> usernames, int[] indices, int start, int end, int index, int combinationSize) {
        if (index == combinationSize) {
            addCombination(partesNome, usernames, indices, combinationSize);
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= combinationSize - index; i++) {
            indices[index] = i;
            combine(partesNome, usernames, indices, i + 1, end, index + 1, combinationSize);
        }
    }

    private void addCombination(String[] partesNome, List<String> usernames, int[] indices, int combinationSize) {
        var sb = new StringBuilder();
        var sbWithDots = new StringBuilder();
        for (int i = 0; i < combinationSize; i++) {
            sb.append(partesNome[indices[i]]);
            sbWithDots.append(partesNome[indices[i]]);
            if (i < combinationSize - 1) {
                sbWithDots.append(".");
            }
        }
        usernames.add(sb.toString());
        usernames.add(sbWithDots.toString());
    }

    default Long gerarId() {
        String dataHora = getCurrentTimestamp();
        return Long.valueOf(dataHora);
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    private String generateRandomNumber(int digits) {
        int upperBound = (int) Math.pow(10, digits);
        int randomNumber = random.nextInt(upperBound);

        return String.format("%0" + digits + "d", randomNumber);
    }

    default Date converterParaData(int ano, int mes, int dia) {
        var calendar = getInstance();
        calendar.set(YEAR, ano);
        calendar.set(MONTH, mes - 1);
        calendar.set(DAY_OF_MONTH, dia);
        return calendar.getTime();
    }


}
