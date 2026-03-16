package teamsheet;

import team.FootballTeam;
import team.Team;

import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import formation.Formation;
import player.Player;

public class TeamsheetPDF {
    
    private FootballTeam team;

    public TeamsheetPDF(FootballTeam team) {
        this.team = team;
    }

    public void generate() {
        System.out.println("Generating teamsheet PDF for team: " + team.getName());
        Document document = new Document();
        String teamName = team.getName();
        String teamNameNoSpaces = teamName.replaceAll("\\s+", "-").toLowerCase();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("teamsheet_" + teamNameNoSpaces + ".pdf"));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD);
            Paragraph title = new Paragraph(teamName, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Starting XI table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setWidths(new int[]{2,8});
            table.addCell("Starting XI");
            table.addCell("Name");

            Formation formation = team.getFormation();

            for (Player p : formation.getOnFieldPlayers()) {
                table.addCell(String.valueOf(team.getPlayerNumber(p)));
                table.addCell(p.getPreferredName());
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // Substitutes table
            PdfPTable subTable = new PdfPTable(2);
            subTable.setWidthPercentage(80);
            subTable.setWidths(new int[]{2,8});
            subTable.addCell("Substitutes");
            subTable.addCell("Name");

            for (Player p : formation.getSubstitutePlayers()) {
                subTable.addCell(String.valueOf(team.getPlayerNumber(p)));
                subTable.addCell(p.getPreferredName());
            }

            document.add(subTable);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

}
