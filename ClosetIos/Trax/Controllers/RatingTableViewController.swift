//
//  RatingTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData

class RatingTableViewController: UITableViewController,UITextFieldDelegate {

    public var ratingContainer : NSPersistentContainer? = (UIApplication.shared.delegate as! AppDelegate).persistentContainer
    
    var fetchedResultsController: NSFetchedResultsController<Rating>?
    
    
    var ratingId:Int16?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }


}
