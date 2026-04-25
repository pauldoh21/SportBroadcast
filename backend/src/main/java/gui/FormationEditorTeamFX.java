package gui;

import team.FootballTeam;
import formation.Formation;
import gui.Layout.GroupFX;
import gui.Layout.PanelNodeFX;

public class FormationEditorTeamFX extends FormationEditorFX {
    protected FootballTeam team;
    protected GroupFX bottomGroup;
    protected SubstitutesPanelFX substitutesPanel;
    protected PegInfoDisplayFX pegInfoDisplay;

    public FormationEditorTeamFX(FootballTeam team) {
        this.team = team;
        super(team.getFormation());
    }

    @Override
    protected void formationSwapEventHandler(FormationSwapEvent e) {
        e.getNewFormation().setTeam(team);
        super.formationSwapEventHandler(e);
        formation.printFormation();
    }

    @Override
    public void setFormation(formation.Formation formation) {
        team.setFormation(formation);
        super.setFormation(formation);
    }

    @Override
    protected void initialiseStack() {
        super.initialiseStack();
        stack.setOnMouseClicked(e -> {
            buttonGroupManager.deselectAll();
            movementArrows.hide();
            pegInfoDisplay.displayPegInfo(null);
        });
    }

    @Override
    protected void initialiseButtons() {
        super.initialiseButtons();
        for (PegButtonFX button : substitutesPanel.getSubsButtons()) {
            if (button instanceof PegButtonSelectableFX psb) {
                this.formationButtons.add(psb);
            }
        }
    }

    protected void initialiseSubsPanel() {
        substitutesPanel =  new SubstitutesPanelFX(team.getFormation(), this.buttonGroupManager);
    }

    protected void initialiseBottomGroup() {
        initialiseSubsPanel();
        pegInfoDisplay = new PegInfoDisplayFX();
        this.addEventHandler(PegSelectedEvent.PEG_SELECTED, e -> {
            if (e.getSourceManager() == this.buttonGroupManager) {
                pegInfoDisplay.displayPegInfo(e.getSelectedPeg());
            }
        });

        bottomGroup = new GroupFX(javafx.collections.FXCollections.observableArrayList(substitutesPanel, pegInfoDisplay), new double[]{0.7, 0.3}, javafx.geometry.Orientation.HORIZONTAL);

    }

    @Override
    protected void setContent() {
        super.setContent();
        initialiseBottomGroup();
        group = new GroupFX(javafx.collections.FXCollections.observableArrayList(formationTemplateRibbon, stackPanel, bottomGroup), new double[]{0.1, 0.75, 0.15}, javafx.geometry.Orientation.VERTICAL);
        this.setContentNode(this.group);
    }

    @Override
    protected double getDisplayHeight() {
        return this.background.getHeight();
    }

    @Override
    protected double getDisplayWidth() {
        return this.background.getWidth();
    }

    @Override
    protected void showFormation() {
        super.showFormation();
        substitutesPanel.setPegButtonScale(this.pegButtonScale);
        substitutesPanel.layoutButtons();
    }
}
