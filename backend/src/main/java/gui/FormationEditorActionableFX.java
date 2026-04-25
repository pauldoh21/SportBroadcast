package gui;

import formation.Peg;
import gui.action.Action;
import gui.action.CardEvent;
import gui.action.GoalEvent;
import gui.action.PlayerEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.StackPane;
import match.FootballMatch;
import match.Match;
import match.event.Goal;
import match.event.RedCard;
import match.event.YellowCard;
import player.Player;
import team.FootballTeam;

public class FormationEditorActionableFX extends FormationEditorTeamFX {
    protected FootballMatch match;
    protected StackPane actionStack;

    public FormationEditorActionableFX(FootballTeam team, FootballMatch match) {
        super(team);
        this.match = match;
        addActionMenu();
        addActionListeners();
    }

    @Override
    protected void initialiseButtonGroupManager() {
        buttonGroupManager = new PegButtonGroupManagerActionableFX();
    }

    @Override
    protected PegButtonFX initialiseButton(Peg peg) {
        PegButtonActionableFX pegButton = new PegButtonActionableFX(peg);
        return pegButton;
    }

    @Override
    protected void initialiseSubsPanel() {
        substitutesPanel =  new SubstitutesPanelActionableFX(team.getFormation(), this.buttonGroupManager);
    }

    @Override
    protected void setContent() {
        super.setContent();
        actionStack = new StackPane(group);
        this.setContentNode(actionStack);
    }

    protected void addActionMenu() {
        actionStack.getChildren().add(getButtonGroupManager().getPegActionMenu());
    }

    protected void addActionListeners() {
        for (Action action : Action.values()) {
            addEventHandler(action.getEventType(), e -> {
                handleEvent(action, (PlayerEvent) e);
            });
        }
    }

    protected void handleEvent(Action actionType, PlayerEvent e) {
        Player p = e.getPlayer();
        if (!team.getPlayers().containsValue(p)) {
            return;
        }
        switch(actionType) {
            case GOAL:
                match.addGoal(team, p);;
                break;
            case YELLOW_CARD:
                match.addEvent(new YellowCard(team, match.getCurrentTime(), p));
                break;
            case RED_CARD:
                match.addEvent(new RedCard(team, match.getCurrentTime(), p));
                break;
            default:
                System.out.println(e.toString() + " event not handled");
        }
    }

    @Override
    public PegButtonGroupManagerActionableFX getButtonGroupManager() {
        return (PegButtonGroupManagerActionableFX) buttonGroupManager;
    }

    @Override
    protected void showFormation() {
        super.showFormation();
        buttonGroupManager.buttonsAdjusted();
    }
    
}
