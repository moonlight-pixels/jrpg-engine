package com.moonlightpixels.jrpg.ui.standard;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.ui.ActorPlacement;
import com.moonlightpixels.jrpg.ui.InputHandler;
import com.moonlightpixels.jrpg.ui.Menu;
import com.moonlightpixels.jrpg.ui.Panel;
import com.moonlightpixels.jrpg.ui.SelectList;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.UserInterface;
import com.moonlightpixels.jrpg.ui.util.LabeledSelectListBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class FrontEndMenu implements StandardMenu {
    private final SelectList.Action newGameAction;
    private final SelectList.Action exitGameAction;
    private ActorPlacement placement;
    private float leftPadding;
    private float rightPadding;
    private float topPadding;
    private float bottomPadding;

    @Override
    public Menu getMenu(final UserInterface userInterface) {
        return new Menu(userInterface, new SingleActorMenuState(new FrontEndSingleActorMenuProvider(), false));
    }

    /**
     * Sets the left, right, top and bottom padding to tthe provided value.
     *
     * @param padding value to set all paddings to.
     */
    public void setPadding(final float padding) {
        setLeftPadding(padding);
        setRightPadding(padding);
        setTopPadding(padding);
        setBottomPadding(padding);
    }

    private final class FrontEndSingleActorMenuProvider implements SingleActorMenuProvider {
        private SelectList selectList;

        @Override
        public Actor getActor(final UiStyle uiStyle) {
            selectList = new LabeledSelectListBuilder(uiStyle)
                .addItem("New Game", newGameAction)
                .addItem("Exit", exitGameAction)
                .build();

            Panel<SelectList> panel = new Panel<SelectList>(
                selectList,
                new Panel.PanelStyle(uiStyle.get(LabeledSelectListBuilder.STYLE, Panel.PanelStyle.class))
            );
            panel.setPlacement(placement);
            panel.padLeft(Math.max(panel.getPadLeft(), leftPadding));
            panel.padRight(Math.max(panel.getPadRight(), rightPadding));
            panel.padTop(Math.max(panel.getPadTop(), topPadding));
            panel.padBottom(Math.max(panel.getPadBottom(), bottomPadding));

            return panel;
        }

        @Override
        public InputHandler getInputHandler() {
            return selectList;
        }
    }

}
