import java.awt.*;
import java.awt.event.*;

/**
 * Set up the GUI and handles events, and lets the rabbit and
 * fox take turns moving.
 * 
 * @author David Matuszek 
 * @version October 24, 2001
 */
public class Controller extends Frame implements Runnable {

    // instance variables -- references to main classes
    private Model model;
    private View view;
    private Controller controller;
    
    // instance variables -- used for program control
    private boolean animationIsRunning = false;
    private int animationDelay;
    private int lookDelay; 
    private boolean needToRedrawEverything = true;
    private boolean showEveryLook = true;
    private boolean singleStepping = false;
    
    // instance "varibles" -- constants for use anywhere
    private static int canvasWidth = 400;
    private static int canvasHeight = 400;
    
    // instance variables -- display elements
    private Canvas canvas = new Canvas();
    private Panel controlPanel = new Panel();
    private Panel buttonPanel = new Panel();
    private Button stepButton = new Button("Step");
    private Button runButton = new Button("Run");
    private Button stopButton = new Button("Stop");
    private Button resetButton = new Button("Reset");
    private Button replayButton = new Button("Replay");
    private Scrollbar speedBar = new Scrollbar(Scrollbar.HORIZONTAL);
    private Label messageLabel = new Label("Let the hunt begin!");
    
    /**
     * Constructs a Controller that uses the given model and view.
     */
    Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.controller = this;
        setTitle("Fox and Rabbit");
        setLocation(50, 50);
        createGui();
        addWindowListener(new WindowCloser());
        canvas.addComponentListener(new RedrawWindow());
    }
    
    /**
     * Starts and controls the animation.
     */
    public void run() {
        while (animationIsRunning && !model.gameIsOver) {
            try { Thread.sleep(animationDelay); }
            catch (InterruptedException e) { }
            step();
        }
    }
    
    /**
     * Tells the model to make one animation "step," then tells
     * the view to display the result.
     */
    private void step() {
        model.allowSingleMove();
        if (model.gameIsOver) {
            if (model.rabbitIsAlive)
                messageLabel.setText("THE RABBIT HAS ESCAPED!");
            else
                messageLabel.setText("THE FOX EATS THE RABBIT AFTER " +
                                     model.stepsTaken + " TURNS!");
            animationIsRunning = false;
            runButton.setEnabled(false);
            stopButton.setEnabled(false);
            stepButton.setEnabled(false);
            resetButton.setEnabled(true);
            replayButton.setEnabled(true);
        }
        else {
           messageLabel.setText("Step number " + model.stepsTaken);
        }
        view.setDisplayLookingAround(singleStepping, lookDelay);
        if (needToRedrawEverything) {
            view.displayEverything();
            needToRedrawEverything = false;
        }
        else {
            view.displayChanges();
        }
    }

    /**
     * Sets up the graphical user interface, including adding actions
     * to the components, and displays it.
     */
    private void createGui() {
    
        // use border layout for main GUI organization
        setLayout(new BorderLayout());
        canvas.setSize(canvasWidth, canvasHeight);
        
        // put a label at the top of the GUI
        add(BorderLayout.NORTH, messageLabel);
        
        // put canvas in main area of GUI
        add(BorderLayout.CENTER, canvas);
        
        // put control panel at bottom of GUI
        add(BorderLayout.SOUTH, controlPanel);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(BorderLayout.WEST, new Label("Speed:"));
        controlPanel.add(BorderLayout.CENTER, speedBar);
        
        // add button panel (with buttons) to control panel
        controlPanel.add(BorderLayout.EAST, buttonPanel);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(replayButton);
        
        // add actions to the controls (see end of this file)
        stepButton.addActionListener(new StepButtonHandler());
        runButton.addActionListener(new RunButtonHandler());
        stopButton.addActionListener(new StopButtonHandler());
        stopButton.setEnabled(false);
        animationIsRunning = false;
        resetButton.addActionListener(new ResetButtonHandler());
        replayButton.addActionListener(new ReplayButtonHandler());
        replayButton.setEnabled(false);
        speedBar.addAdjustmentListener(new SpeedBarListener());
        
        // finish up and display the GUI
        pack();
        Rectangle rectangle = speedBar.getBounds();
        rectangle.y = rectangle.y + 8;
        rectangle.height = rectangle.height - 16;
        rectangle.width = rectangle.width - 20;
        speedBar.setBounds(rectangle);
        setVisible(true);
        speedBar.setBounds(rectangle);
        speedBar.setValue(50);
        setDelays(50);
        view.setCanvas(canvas); // cannot be done until visible
        view.displayEverything();
    }
    
    /**
     * Changes the size of the Speed scrollbar in an attempt to make
     * it look reasonable regardless of the window size.
     */
    private void resizeSpeedBar() {
        Rectangle rectangle = speedBar.getBounds();
        rectangle.y = rectangle.y + 8;
        rectangle.height = Math.max(4, rectangle.height - 16);
        rectangle.width = Math.max(50, rectangle.width - 20);
        speedBar.setBounds(rectangle);
        setVisible(true);
        speedBar.setBounds(rectangle);
    }
    
    /**
     * Computes the animation delay and the delay between "looks," based
     * on the current value of the speedBar.
     *
     * @param value  current value returned from the speedBar (0..90)
     */
    private void setDelays(int value) {
        animationDelay = (int)(2320 - 500 * Math.log(value + 10));
        lookDelay = animationDelay / 2 + 10;
    }
        
    /**
     * Handles a click on the close window icon by closing the
     * window and quitting the program.
     */
    class WindowCloser extends WindowAdapter {
    
        public void windowClosing(WindowEvent e) {
            dispose();
            System.exit(0);
        }
    }
    
    /**
     * Inner class for handling a window show or window resize
     * operation.
     */
    class RedrawWindow extends ComponentAdapter {
    
        /**
         * When the window is resized, notifies the view of the
         * new size, and sets a flag so that everything on the
         * canvas will be redrawn.
         *
         * @param e  parameter is ignored.
         */
        public void componentResized(ComponentEvent e) {
            resizeSpeedBar();
            view.setCanvas(canvas);
            needToRedrawEverything = true;
        }

        /**
         * When the window is resshown,  sets a flag so that everything
         * on the canvas will be redrawn.
         *
         * @param e  parameter is ignored.
         */
        public void componentShown(ComponentEvent e) {
            needToRedrawEverything = true;
        }
    }
    
    /**
     * Inner class for handling the Step button.
     */
    class StepButtonHandler implements ActionListener {
    
        /**
         * Handles the Step button. If the animation is running, 
         * the Step button just halts it (by setting the
         * <code>programIsRunning</code> flag to false); otherwise, the
         * rabbit hunt is advanced one step. While single stepping,
         * all buttons except the Stop button should be enabled.
         *
         * @param e the Event that invoked this handler (ignored).
         */
        public void actionPerformed(ActionEvent e) {
        
            singleStepping = true;
            
            if (animationIsRunning) {
                animationIsRunning = false;
                
                // adjust button states when game ends
                if (!model.gameIsOver) {
                    runButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(true);
                }
            }
            else {
                step();
            }
            replayButton.setEnabled(true);
        }
    }

    /**
     * Inner class for handling the Run button.
     */
    class RunButtonHandler implements ActionListener {
    
        /**
         * Sets the <code>programIsRunning</code> flag to true, and
         * creates and starts an animation Thread to do the animation.
         * While the animation is running, all buttons except the Run
         * button should be enabled.
         *
         * @param e the Event that invoked this handler (ignored).
         */
        public void actionPerformed(ActionEvent e) {
            runButton.setEnabled(false);
            stopButton.setEnabled(true);
            resetButton.setEnabled(false);
            replayButton.setEnabled(false);

            Thread animationThread = new Thread(controller);
            animationIsRunning = true;
            singleStepping = false;
            animationThread.start();
        }
    }

    /**
     * Inner class for handling the Stop button.
     */
    class StopButtonHandler implements ActionListener {
    
        /**
         * Sets the <code>programIsRunning</code> flag to false, thus
         * causing run() to end the Thread doing the animation.
         * While the animation is stopped, all buttons except
         * the Stop button should be enabled.
         *
         * @param e the Event that invoked this handler (ignored).
         */
        public void actionPerformed(ActionEvent e) {
            animationIsRunning = false;
            runButton.setEnabled(true);
            stopButton.setEnabled(false);
            resetButton.setEnabled(true);
            replayButton.setEnabled(true);
        }
    }
    
    /**
     * Inner class for handling the Reset button.
     */
    class ResetButtonHandler implements ActionListener {
    
        /**
         * Recreates the entire setup, including placement of
         * bushes and animals. Cannot be performed while the
         * animation is running. Afterwards, all buttons except
         * the Stop button should be enabled.
         *
         * @param e the Event that invoked this handler (ignored).
         */
        public void actionPerformed(ActionEvent e) {
            model.reset();
            messageLabel.setText("New game");
            runButton.setEnabled(true);
            stopButton.setEnabled(false);
            stepButton.setEnabled(true);
            replayButton.setEnabled(false);
            view.displayEverything();
            needToRedrawEverything = false;
            singleStepping = false;
        }
    }

    /**
     * Inner class for handling the Replay button.
     */
    class ReplayButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.replay();
            messageLabel.setText("Instant replay");
            runButton.setEnabled(true);
            stopButton.setEnabled(false);
            stepButton.setEnabled(true);
            view.displayEverything();
            needToRedrawEverything = false;
            singleStepping = false;
        }
    }
    
    /**
     * Inner class for handling the SpeedBar control.
     */
    class SpeedBarListener implements AdjustmentListener {
    
        /**
         * Handles the SpeedBar control. Speed control is nonlinear,
         * and the formula used is a hack job that should be replaced.
         *
         * @param e the Event that invoked this handler (ignored).
         */
        public void adjustmentValueChanged(AdjustmentEvent e) {
            int scrollBarValue = e.getValue();
            setDelays(scrollBarValue);
            messageLabel.setText("scrollbar = " + scrollBarValue +
                                 ", delay = " + animationDelay);
        }
    }
}
