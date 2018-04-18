//
//  ToiletInformationsTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit

class ToiletInformationsTableViewController: UITableViewController {
    var editable:Bool = false
    
    @IBOutlet weak var nameLabel: UILabel!
        {
        didSet{
            nameLabel.text = toiletName
        }
    }
    
    @IBOutlet weak var ratingLabel: UILabel!
        {
        didSet{
            ratingLabel.text = String(format:"%.02f", (ratingValue)!)
        }
    }
    
    @IBOutlet weak var statusLabel: UILabel!
        {
        didSet{
            statusLabel.text = status
        }
    }
    
    @IBOutlet weak var newRatingValue: UISlider!
    
    @IBAction func rateHappened(_ sender: UIButton) {
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let ratingController = segue.destination as? RatingTableViewController{
            let tableView = UITableView.init()
            tableView.delegate = ratingController
            ratingController.view = tableView
            let container = ( UIApplication.shared.delegate as! AppDelegate ).persistentContainer
            container.performBackgroundTask{ context in
//                let toilet = try! Toilet.findToilet(withId: (self.toiletId)!, in: context)
//                DispatchQueue.main.async {
//                    print(toilet?.ratings)
//                }
                let toilet = try! context.fetch(Rating.fetchRequest())
                if let ratings = toilet as? [Rating]{
                    for rating in ratings{
                        print(rating.toilet?.id)
                    }
                }
            }
            
            
        }
    }

    
    public var toiletId:Int16?
    
    public var toiletName:String?

    public var ratingValue: Float?

    public var status:String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
//        nameLabel.text = showedToilet?.name!
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 5
    }

    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
