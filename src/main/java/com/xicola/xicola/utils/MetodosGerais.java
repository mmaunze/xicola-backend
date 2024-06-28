package com.xicola.xicola.utils;

import jakarta.validation.constraints.NotNull;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JTextField;

public interface MetodosGerais {


    public default Date converterStringParaData(String dataString) throws ParseException {
        var formato = new SimpleDateFormat("dd/MM/yyyy");
        var dataUtil = formato.parse(dataString);
        return new Date(dataUtil.getTime());
    }

    public default String converterDataParaString(@NotNull LocalDate data) {
        var df = new SimpleDateFormat("yyyy-MM-dd");
        var dataFormatada = df.format(data);
        var partes = dataFormatada.split("-");
        return String.format("%s/%s/%s", partes[2], partes[1], partes[0]);
    }

    public default int calcularIdade(String dataNascimento) {
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


    public default String calcularFaixaEtaria(int idade) {
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

    public default String obterMesPorExtenso(int numeroMes) {
        if (numeroMes < 1 || numeroMes > 12) {
            throw new IllegalArgumentException("Número de mês inválido. Deve estar entre 1 e 12.");
        }
        var meses = new DateFormatSymbols(new Locale("pt", "BR")).getMonths();
        return meses[numeroMes - 1];
    }


    public default void habilitarCampo(JTextField campo) {
        campo.setEnabled(true);
        campo.setEditable(true);
        campo.setFocusable(true);
        campo.requestFocus();
    }

    public default int obterNumeroMes(String nomeMes) {
        var symbols = new DateFormatSymbols(new Locale("pt", "BR"));
        var meses = symbols.getMonths();
        for (var i = 0; i < meses.length; i++) {
            if (meses[i].equalsIgnoreCase(nomeMes)) {
                return i + 1;
            }
        }
        return 0;
    }

    default List<String> gerarUsernames(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new IllegalArgumentException("Nome completo não pode estar vazio");
        }

        var partesNome = nomeCompleto.trim().toLowerCase().split("\\s+");
        var numPalavras = partesNome.length;
        List<String> usernames = new ArrayList<>();

        // Combinações com iniciais e nome completo
        var sb = new StringBuilder();
        for (var parte : partesNome) {
            sb.append(parte.charAt(0));
        }
        usernames.add(sb.toString() + partesNome[numPalavras - 1]);
        usernames.add(sb.toString() + "." + partesNome[numPalavras - 1]);

        // Combinando até 5 palavras do nome
        if (numPalavras >= 2) {
            // Combinações com duas palavras
            for (var i = 0; i < numPalavras - 1; i++) {
                for (var j = i + 1; j < numPalavras; j++) {
                    usernames.add(partesNome[i] + partesNome[j]);
                    usernames.add(partesNome[j] + "." + partesNome[i]);
                }
            }
        }

        if (numPalavras >= 3) {
            // Combinações com três palavras
            for (var i = 0; i < numPalavras - 2; i++) {
                for (var j = i + 1; j < numPalavras - 1; j++) {
                    for (var k = j + 1; k < numPalavras; k++) {
                        usernames.add(partesNome[i] + partesNome[j] + partesNome[k]);
                        usernames.add(partesNome[k] + "." + partesNome[i] + "." + partesNome[j]);
                    }
                }
            }
        }

        if (numPalavras >= 4) {
            // Combinações com quatro palavras
            for (var i = 0; i < numPalavras - 3; i++) {
                for (var j = i + 1; j < numPalavras - 2; j++) {
                    for (var k = j + 1; k < numPalavras - 1; k++) {
                        for (var l = k + 1; l < numPalavras; l++) {
                            usernames.add(partesNome[i] + partesNome[j] + partesNome[k] + partesNome[l]);
                            usernames.add(
                                    partesNome[l] + "." + partesNome[i] + "." + partesNome[j] + "." + partesNome[k]);
                        }
                    }
                }
            }
        }

        if (numPalavras >= 5) {
            // Combinações com cinco palavras
            for (var i = 0; i < numPalavras - 4; i++) {
                for (var j = i + 1; j < numPalavras - 3; j++) {
                    for (var k = j + 1; k < numPalavras - 2; k++) {
                        for (var l = k + 1; l < numPalavras - 1; l++) {
                            for (var m = l + 1; m < numPalavras; m++) {
                                usernames.add(
                                        partesNome[i] + partesNome[j] + partesNome[k] + partesNome[l] + partesNome[m]);
                                usernames.add(partesNome[m] + "." + partesNome[i] + "." + partesNome[j] + "."
                                        + partesNome[k] + "." + partesNome[l]);
                            }
                        }
                    }
                }
            }
        }

        return usernames;
    }

    default String gerarId() {
        var sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        var dataHora = sdf.format(new java.util.Date());
        var randomNumber = new java.util.Random().nextInt(1000); // Número aleatório de 3 dígitos
        return dataHora + String.format("%03d", randomNumber);
    }

    public default Date converterParaData(int ano, int mes, int dia) {
        var calendar = getInstance();
        calendar.set(YEAR, ano);
        calendar.set(MONTH, mes - 1); // Mês em Java é baseado em zero (janeiro = 0, fevereiro = 1, ...)
        calendar.set(DAY_OF_MONTH, dia);
        return calendar.getTime();
    }

   
}
